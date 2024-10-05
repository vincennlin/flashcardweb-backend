package com.vincennlin.courseservice.controller;

import com.vincennlin.courseservice.constant.AppConstants;
import com.vincennlin.courseservice.payload.course.dto.CourseDto;
import com.vincennlin.courseservice.payload.course.page.CoursePageResponse;
import com.vincennlin.courseservice.payload.request.CreateCourseRequest;
import com.vincennlin.courseservice.service.CourseService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1")
public class CourseController implements CourseControllerSwagger {

    private final CourseService courseService;

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/courses")
    public ResponseEntity<CoursePageResponse> getAllCourses(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {

        Pageable pageable = PageRequest.of(pageNo, pageSize,
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        CoursePageResponse coursePageResponse = courseService.getAllCourses(pageable);

        return new ResponseEntity<>(coursePageResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/courses/{course_id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable(name = "course_id") @Min(1) Long courseId) {

        CourseDto course = courseService.getCourseById(courseId);

        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/courses")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CreateCourseRequest request) {

        CourseDto newCourse = courseService.createCourse(request);

        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/courses/{course_id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable(name = "course_id") @Min(1) Long courseId,
                                                  @RequestBody CourseDto courseDto) {

        CourseDto updatedCourse = courseService.updateCourse(courseId, courseDto);

        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/courses/{course_id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable(name = "course_id") @Min(1) Long courseId) {

        courseService.deleteCourse(courseId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
