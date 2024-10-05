package com.vincennlin.courseservice.service.impl;

import com.vincennlin.courseservice.entity.Course;
import com.vincennlin.courseservice.exception.ResourceNotFoundException;
import com.vincennlin.courseservice.mapper.CourseMapper;
import com.vincennlin.courseservice.payload.course.dto.CourseDto;
import com.vincennlin.courseservice.payload.course.page.CoursePageResponse;
import com.vincennlin.courseservice.payload.request.CreateCourseRequest;
import com.vincennlin.courseservice.repository.CourseRepository;
import com.vincennlin.courseservice.service.AuthService;
import com.vincennlin.courseservice.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;

    private final AuthService authService;

    private final CourseRepository courseRepository;

    @Override
    public CoursePageResponse getAllCourses(Pageable pageable) {

        return getCoursePageResponse(courseRepository.findAll(pageable));
    }

    @Override
    public CourseDto getCourseById(Long courseId) {

        Course course = getCourseEntityById(courseId);

        return courseMapper.mapToDto(course);
    }

    @Override
    public CourseDto createCourse(CreateCourseRequest request) {

        Long userId = authService.getCurrentUserId();

        Course course = new Course(request.getName(), userId);

        Course savedCourse = courseRepository.save(course);

        return courseMapper.mapToDto(savedCourse);
    }

    @Override
    public CourseDto updateCourse(Long courseId, CourseDto courseDto) {

        Course course = getCourseEntityById(courseId);

        course.setName(courseDto.getName());

        Course updatedCourse = courseRepository.save(course);

        return courseMapper.mapToDto(updatedCourse);
    }

    @Override
    public void deleteCourse(Long courseId) {

        Course course = getCourseEntityById(courseId);

        authService.authorizeOwnership(course.getCreatorId());

        courseRepository.delete(course);
    }

    private Course getCourseEntityById(Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId.toString()));

        authService.authorizeOwnership(course.getCreatorId());

        return course;
    }

    private CoursePageResponse getCoursePageResponse(Page<Course> pageOfCourses) {
        List<Course> listOfCourses = pageOfCourses.getContent();

        List<CourseDto> CourseDtoList = listOfCourses.stream().map(courseMapper::mapToDto).toList();

        CoursePageResponse coursePageResponse = new CoursePageResponse();
        coursePageResponse.setContent(CourseDtoList);
        coursePageResponse.setPageNo(pageOfCourses.getNumber());
        coursePageResponse.setPageSize(pageOfCourses.getSize());
        coursePageResponse.setTotalElements(pageOfCourses.getTotalElements());
        coursePageResponse.setTotalPages(pageOfCourses.getTotalPages());
        coursePageResponse.setLast(pageOfCourses.isLast());

        return coursePageResponse;
    }
}
