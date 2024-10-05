package com.vincennlin.courseservice.mapper;

import com.vincennlin.courseservice.entity.Course;
import com.vincennlin.courseservice.payload.course.dto.CourseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    private final ModelMapper modelMapper;

    public CourseMapper() {
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public CourseDto mapToDto(Course course) {
        CourseDto courseDto = modelMapper.map(course, CourseDto.class);
        courseDto.setFlashcardCount(course.getFlashcardCount());
        courseDto.setUserCount(course.getUserCount());
        return courseDto;
    }
}
