package com.vincennlin.courseservice.service;

import com.vincennlin.courseservice.payload.course.dto.CourseDto;
import com.vincennlin.courseservice.payload.course.page.CoursePageResponse;
import com.vincennlin.courseservice.payload.flashcard.FlashcardDto;
import com.vincennlin.courseservice.payload.request.CreateCourseRequest;
import com.vincennlin.courseservice.payload.request.FlashcardIdsRequest;
import com.vincennlin.courseservice.payload.request.CopyFlashcardsToDeckRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface CourseService {

    CoursePageResponse getAllCourses(Pageable pageable);

    CourseDto getCourseById(Long courseId);

    Set<Long> getFlashcardIdsByCourseId(Long courseId);

    CourseDto createCourse(CreateCourseRequest request);

    CourseDto updateCourse(Long courseId, CourseDto courseDto);

    void deleteCourse(Long courseId);

    CourseDto enrollCourse(Long courseId);

    CourseDto leaveCourse(Long courseId);

    CourseDto addFlashcardsToCourse(Long courseId, FlashcardIdsRequest request);

    CourseDto removeFlashcardsFromCourse(Long courseId, FlashcardIdsRequest request);

    List<FlashcardDto> copyFlashcardsToDeck(Long courseId, CopyFlashcardsToDeckRequest request);
}
