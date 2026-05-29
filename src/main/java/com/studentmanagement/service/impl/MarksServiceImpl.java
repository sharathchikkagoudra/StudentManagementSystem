package com.studentmanagement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.studentmanagement.dto.CourseMarksResponseDto;
import com.studentmanagement.dto.FailedStudentResponseDto;
import com.studentmanagement.dto.MarksRequestDto;
import com.studentmanagement.dto.PassPercentageResponseDto;
import com.studentmanagement.dto.RankResponseDto;
import com.studentmanagement.dto.ResultResponseDto;
import com.studentmanagement.dto.StudentMarksResponseDto;
import com.studentmanagement.dto.SubjectResultDto;
import com.studentmanagement.entity.Course;
import com.studentmanagement.entity.Marks;
import com.studentmanagement.entity.Student;
import com.studentmanagement.exception.StudentNotFoundException;
import com.studentmanagement.repository.CourseRepository;
import com.studentmanagement.repository.MarksRepository;
import com.studentmanagement.repository.StudentRepository;
import com.studentmanagement.service.MarksService;

@Service
public class MarksServiceImpl implements MarksService{
	private final MarksRepository marksRepository;
	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;
	
	public MarksServiceImpl(MarksRepository marksRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
		this.marksRepository = marksRepository;
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;	
	}
	
	@Override
	public Marks addMarks(MarksRequestDto marksRequestDto) {
		Student student = studentRepository.findById(marksRequestDto.getStudentId())
				.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + marksRequestDto.getStudentId()));
		
		Course course = courseRepository.findById(marksRequestDto.getCourseId())
				.orElseThrow(() -> new RuntimeException("Course not found with id:" + marksRequestDto.getCourseId()));
		
		if(marksRepository.existsByStudentIdAndCourseId(marksRequestDto.getStudentId(), marksRequestDto.getCourseId())) {
			throw new RuntimeException("Marks already exist for this student and course");
		}
		
		Marks marks = new Marks();
		marks.setStudent(student);
		marks.setCourse(course);
		marks.setMarksObtained(marksRequestDto.getMarksObtained());
		
		return marksRepository.save(marks);
		
	}

	@Override
	public List<StudentMarksResponseDto> getAllMarks() {

	    List<Marks> allMarks = marksRepository.findAll();

	    Map<Long, List<Marks>> groupedMarks = allMarks.stream()
	            .collect(Collectors.groupingBy(mark -> mark.getStudent().getId()));

	    List<StudentMarksResponseDto> response = new ArrayList<>();

	    for (List<Marks> marksList : groupedMarks.values()) {

	        Student student = marksList.get(0).getStudent();

	        List<CourseMarksResponseDto> courseMarks = marksList.stream()
	                .map(mark -> new CourseMarksResponseDto(

	                	    mark.getCourse().getId(),

	                	    mark.getCourse().getCourseName(),

	                	    mark.getCourse().getCourseCode(),

	                	    mark.getMarksObtained()
	                	))
	                .toList();

	        StudentMarksResponseDto dto = new StudentMarksResponseDto(
	                student.getId(),
	                student.getName(),
	                student.getUsn(),
	                courseMarks
	        );

	        response.add(dto);
	    }

	    return response;
	}

	@Override
	public StudentMarksResponseDto getMarksByStudentId(Long studentId) {

	    Student student = studentRepository.findById(studentId)
	            .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));

	    List<Marks> marksList = marksRepository.findByStudentId(studentId);

	    List<CourseMarksResponseDto> courseMarks = marksList.stream()
	            .map(mark -> new CourseMarksResponseDto(

                	    mark.getCourse().getId(),

                	    mark.getCourse().getCourseName(),

                	    mark.getCourse().getCourseCode(),

                	    mark.getMarksObtained()
                	))
	            .toList();

	    return new StudentMarksResponseDto(
	            student.getId(),
	            student.getName(),
	            student.getUsn(),
	            courseMarks
	    );
	}
	
	@Override
	public Marks updateMarks(MarksRequestDto marksRequestDto) {

	    Marks existingMarks = marksRepository.findByStudentIdAndCourseId(
	            marksRequestDto.getStudentId(),
	            marksRequestDto.getCourseId()
	    ).orElseThrow(() -> new RuntimeException(
	            "Marks not found for student id " + marksRequestDto.getStudentId()
	                    + " and course id " + marksRequestDto.getCourseId()
	    ));

	    existingMarks.setMarksObtained(marksRequestDto.getMarksObtained());

	    return marksRepository.save(existingMarks);
	}
	
	@Override
	public void deleteMarks(Long studentId, Long courseId) {

	    Marks marks = marksRepository.findByStudentIdAndCourseId(studentId, courseId)
	            .orElseThrow(() -> new RuntimeException(
	                    "Marks not found for student id " + studentId +
	                    " and course id " + courseId
	            ));

	    marksRepository.delete(marks);
	}

	@Override
	public ResultResponseDto getStudentResult(Long studentId) {
		    Student student = studentRepository.findById(studentId)
		            .orElseThrow(() ->
		                    new StudentNotFoundException("Student not found with id: " + studentId));

		    List<Marks> marksList = marksRepository.findByStudentId(studentId);

		    if (marksList.isEmpty()) {
		        throw new RuntimeException("No marks found for student id: " + studentId);
		    }

		    List<SubjectResultDto> subjects = new ArrayList<>();

		    int totalMarks = 0;

		    boolean hasFailed = false;

		    for (Marks marks : marksList) {

		        int obtainedMarks = marks.getMarksObtained();

		        totalMarks += obtainedMarks;

		        String grade;
		        String result;

		        if (obtainedMarks < 35) {
		            grade = "F";
		            result = "FAIL";
		            hasFailed = true;
		        }
		        else if (obtainedMarks >= 90) {
		            grade = "A+";
		            result = "PASS";
		        }
		        else if (obtainedMarks >= 80) {
		            grade = "A";
		            result = "PASS";
		        }
		        else if (obtainedMarks >= 70) {
		            grade = "B+";
		            result = "PASS";
		        }
		        else if (obtainedMarks >= 60) {
		            grade = "B";
		            result = "PASS";
		        }
		        else if (obtainedMarks >= 50) {
		            grade = "C+";
		            result = "PASS";
		        }
		        else {
		            grade = "C";
		            result = "PASS";
		        }

		        SubjectResultDto subject = new SubjectResultDto(
		                marks.getCourse().getCourseName(),
		                marks.getCourse().getCourseCode(),
		                obtainedMarks,
		                grade,
		                result
		        );

		        subjects.add(subject);
		    }

		    double percentage = (double) totalMarks / marksList.size();

		    String finalResult = hasFailed ? "FAIL" : "PASS";

		    return new ResultResponseDto(
		            student.getId(),
		            student.getName(),
		            student.getUsn(),
		            subjects,
		            totalMarks,
		            percentage,
		            finalResult
		    );
		
	}
	
	@Override
	public List<RankResponseDto> getRankList() {

	    List<Student> students = studentRepository.findAll();

	    List<RankResponseDto> rankList = new ArrayList<>();

	    for (Student student : students) {

	        List<Marks> marksList = marksRepository.findByStudentId(student.getId());

	        if (marksList.isEmpty()) {
	            continue;
	        }

	        int totalMarks = 0;

	        boolean hasFailed = false;

	        for (Marks marks : marksList) {

	            int obtainedMarks = marks.getMarksObtained();

	            totalMarks += obtainedMarks;

	            if (obtainedMarks < 35) {
	                hasFailed = true;
	            }
	        }

	        double percentage = (double) totalMarks / marksList.size();

	        String finalResult = hasFailed ? "FAIL" : "PASS";

	        RankResponseDto dto = new RankResponseDto(
	                null,
	                student.getName(),
	                student.getUsn(),
	                percentage,
	                finalResult
	        );

	        rankList.add(dto);
	    }

	    rankList.sort((s1, s2) ->
	            Double.compare(s2.getPercentage(), s1.getPercentage()));

	    int currentRank = 1;

	    for (int i = 0; i < rankList.size(); i++) {

	        if (i > 0 &&
	                !rankList.get(i).getPercentage()
	                        .equals(rankList.get(i - 1).getPercentage())) {

	            currentRank++;
	        }

	        rankList.get(i).setRank(currentRank);
	    }

	    return rankList;
	}
	
	@Override
	public List<RankResponseDto> getTopperStudents() {

	    List<RankResponseDto> rankList = getRankList();

	    if (rankList.isEmpty()) {
	        throw new RuntimeException("No students found");
	    }

	    return rankList.stream()
	            .filter(student -> student.getRank() == 1)
	            .toList();
	}
	
	@Override
	public List<FailedStudentResponseDto> getFailedStudents() {

	    List<ResultResponseDto> results = getAllStudentResults();

	    List<FailedStudentResponseDto> failedStudents = new ArrayList<>();

	    for (ResultResponseDto result : results) {

	        if (result.getFinalResult().equals("FAIL")) {

	            List<String> failedSubjects = new ArrayList<>();

	            for (SubjectResultDto subject : result.getSubjects()) {

	                if (subject.getResult().equalsIgnoreCase("Fail")) {

	                    failedSubjects.add(subject.getCourseName());
	                }
	            }

	            FailedStudentResponseDto dto = new FailedStudentResponseDto(

	                            result.getStudentId(),

	                            result.getStudentName(),

	                            result.getStudentUsn(),

	                            failedSubjects,

	                            result.getPercentage(),

	                            result.getFinalResult()
	                    );

	            failedStudents.add(dto);
	        }
	    }

	    return failedStudents;
	}
	
	@Override
	public PassPercentageResponseDto getPassPercentage() {

	    List<RankResponseDto> rankList = getRankList();

	    int totalStudents = rankList.size();

	    int passedStudents = (int) rankList.stream()
	            .filter(student -> student.getFinalResult().equals("PASS"))
	            .count();

	    int failedStudents = totalStudents - passedStudents;

	    double passPercentage;

	    if (totalStudents == 0) {
	        passPercentage = 0;
	    } else {
	        passPercentage = ((double) passedStudents / totalStudents) * 100;
	    }

	    return new PassPercentageResponseDto(
	            totalStudents,
	            passedStudents,
	            failedStudents,
	            passPercentage
	    );
	}
	
	@Override
	public List<ResultResponseDto> getAllStudentResults() {

	    List<Student> students =
	            studentRepository.findAll();

	    List<ResultResponseDto> results =
	            new ArrayList<>();

	    for (Student student : students) {

	        List<Marks> marksList =
	                marksRepository.findByStudentId(
	                        student.getId()
	                );

	        if (marksList.isEmpty()) {
	            continue;
	        }

	        List<SubjectResultDto> subjects =
	                new ArrayList<>();

	        int totalMarks = 0;

	        boolean hasFailed = false;

	        for (Marks marks : marksList) {

	            int obtainedMarks =
	                    marks.getMarksObtained();

	            totalMarks += obtainedMarks;

	            String grade;

	            if (obtainedMarks >= 90) {
	                grade = "A+";
	            }

	            else if (obtainedMarks >= 80) {
	                grade = "A";
	            }

	            else if (obtainedMarks >= 70) {
	                grade = "B+";
	            }

	            else if (obtainedMarks >= 60) {
	                grade = "B";
	            }

	            else if (obtainedMarks >= 50) {
	                grade = "C+";
	            }

	            else if (obtainedMarks >= 35) {
	                grade = "C";
	            }

	            else {
	                grade = "F";
	            }

	            String result =
	                    obtainedMarks >= 35
	                            ? "Pass"
	                            : "Fail";

	            if (obtainedMarks < 35) {
	                hasFailed = true;
	            }

	            SubjectResultDto subjectDto =
	                    new SubjectResultDto(

	                            marks.getCourse().getCourseName(),

	                            marks.getCourse().getCourseCode(),

	                            obtainedMarks,

	                            grade,

	                            result
	                    );

	            subjects.add(subjectDto);
	        }

	        double percentage =
	                (double) totalMarks / marksList.size();

	        String finalResult =
	                hasFailed
	                        ? "FAIL"
	                        : "PASS";

	        ResultResponseDto resultDto =
	                new ResultResponseDto(

	                        student.getId(),

	                        student.getName(),

	                        student.getUsn(),

	                        subjects,

	                        totalMarks,

	                        percentage,

	                        finalResult
	                );

	        results.add(resultDto);
	    }

	    return results;
	}
	
	@Override
	public List<ResultResponseDto> searchResultsByUsn(String usn) {

	    List<Student> students =
	            studentRepository
	                    .findByUsnContainingIgnoreCase(usn);

	    List<ResultResponseDto> results =
	            new ArrayList<>();

	    for (Student student : students) {

	        List<Marks> marksList =
	                marksRepository.findByStudentId(
	                        student.getId()
	                );

	        if (marksList.isEmpty()) {
	            continue;
	        }

	        List<SubjectResultDto> subjects =
	                new ArrayList<>();

	        int totalMarks = 0;

	        boolean hasFailed = false;

	        for (Marks marks : marksList) {

	            int obtainedMarks =
	                    marks.getMarksObtained();

	            totalMarks += obtainedMarks;

	            String grade;

	            if (obtainedMarks >= 90) {
	                grade = "A+";
	            }

	            else if (obtainedMarks >= 80) {
	                grade = "A";
	            }

	            else if (obtainedMarks >= 70) {
	                grade = "B+";
	            }

	            else if (obtainedMarks >= 60) {
	                grade = "B";
	            }

	            else if (obtainedMarks >= 50) {
	                grade = "C+";
	            }

	            else if (obtainedMarks >= 35) {
	                grade = "C";
	            }

	            else {
	                grade = "F";
	            }

	            String result =
	                    obtainedMarks >= 35
	                            ? "Pass"
	                            : "Fail";

	            if (obtainedMarks < 35) {
	                hasFailed = true;
	            }

	            SubjectResultDto subjectDto =
	                    new SubjectResultDto(

	                            marks.getCourse().getCourseName(),

	                            marks.getCourse().getCourseCode(),

	                            obtainedMarks,

	                            grade,

	                            result
	                    );

	            subjects.add(subjectDto);
	        }

	        double percentage =
	                (double) totalMarks / marksList.size();

	        String finalResult =
	                hasFailed
	                        ? "FAIL"
	                        : "PASS";

	        ResultResponseDto dto =
	                new ResultResponseDto(

	                        student.getId(),

	                        student.getName(),

	                        student.getUsn(),

	                        subjects,

	                        totalMarks,

	                        percentage,

	                        finalResult
	                );

	        results.add(dto);
	    }

	    return results;
	}
	
	@Override
	public List<RankResponseDto> searchRankListByUsn(String usn) {

	    List<RankResponseDto> rankList = getRankList();

	    return rankList.stream()

	            .filter(rank ->

	                    rank.getStudentUsn()
	                            .toLowerCase()
	                            .contains(usn.toLowerCase())
	            )

	            .toList();
	}
	
	@Override
	public List<FailedStudentResponseDto> searchFailedStudentsByUsn(String usn) {

	    List<FailedStudentResponseDto> failedStudents = getFailedStudents();

	    return failedStudents.stream()

	            .filter(student ->

	                    student.getStudentUsn()
	                            .toLowerCase()
	                            .contains(usn.toLowerCase())
	            )

	            .toList();
	}
}
