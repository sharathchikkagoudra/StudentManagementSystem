package com.studentmanagement.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.studentmanagement.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> map = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors()
							.forEach(err -> map.put(err.getField(), err.getDefaultMessage()));
		return new ApiResponse<>(
				false,
				"Validation Failed",
				map
		);
	}
	
	@ExceptionHandler(StudentNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse<Map<String, String>> handlehandleStudentNotFoundException(StudentNotFoundException ex) {
		Map<String, String> map = new HashMap<>();
		
		map.put("messege", ex.getMessage());
		return new ApiResponse<>(
			false,
			ex.getMessage(),
			null
		);
	}
	
	@ExceptionHandler(DepartmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Map<String, String>> handleDepartmentNotFoundException(DepartmentNotFoundException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        return new ApiResponse<>(
        	false,
        	ex.getMessage(),
        	null
        );
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Map<String, String>> handleRuntimeException(RuntimeException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        return new ApiResponse<>(
        	false,
        	ex.getMessage(),
        	null
        );
    }
}
