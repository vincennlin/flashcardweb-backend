package com.vincennlin.courseservice.service;

import com.vincennlin.courseservice.payload.course.dto.CourseDto;
import com.vincennlin.courseservice.payload.course.page.CoursePageResponse;
import com.vincennlin.courseservice.payload.request.CreateCourseRequest;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    CoursePageResponse getAllCourses(Pageable pageable);

    CourseDto getCourseById(Long courseId);

    CourseDto createCourse(CreateCourseRequest request);

    CourseDto updateCourse(Long courseId, CourseDto courseDto);

    void deleteCourse(Long courseId);
}
