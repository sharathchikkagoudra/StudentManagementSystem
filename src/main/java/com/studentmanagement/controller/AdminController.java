package com.studentmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.studentmanagement.dto.*;

import com.studentmanagement.service.AdminService;

@RestController

@RequestMapping("/api/admin")

public class AdminController {

    @Autowired

    private AdminService adminService;

    @PostMapping("/register")

    public ApiResponse<String> register(

            @RequestBody
            AdminRequestDto dto
    ) {

        return new ApiResponse<>(

                true,

                adminService.register(dto),

                null
        );
    }

    @PostMapping("/login")

    public ApiResponse<String> login(

            @RequestBody
            AdminRequestDto dto
    ) {

        return new ApiResponse<>(

                true,

                adminService.login(dto),

                null
        );
    }

    @PutMapping("/change-password")

    public ApiResponse<String> changePassword(

            @RequestBody
            ChangePasswordDto dto
    ) {

        return new ApiResponse<>(

                true,

                adminService.changePassword(dto),

                null
        );
    }
}