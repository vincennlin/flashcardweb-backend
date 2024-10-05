package com.vincennlin.courseservice.service;

import com.vincennlin.courseservice.payload.course.dto.CourseDto;
import com.vincennlin.courseservice.payload.course.page.CoursePageResponse;
import com.vincennlin.courseservice.payload.request.CreateCourseRequest;
import com.vincennlin.courseservice.payload.request.FlashcardIdsRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {

    CoursePageResponse getAllCourses(Pageable pageable);

    CourseDto getCourseById(Long courseId);

    CourseDto createCourse(CreateCourseRequest request);

    CourseDto updateCourse(Long courseId, CourseDto courseDto);

    void deleteCourse(Long courseId);

    CourseDto enrollCourse(Long courseId);

    CourseDto leaveCourse(Long courseId);

    CourseDto addFlashcardsToCourse(Long courseId, FlashcardIdsRequest request);

    CourseDto removeFlashcardsFromCourse(Long courseId, FlashcardIdsRequest request);
}
