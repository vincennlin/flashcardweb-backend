package com.vincennlin.flashcardservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Set;

@FeignClient(name = "course-ws")
public interface CourseServiceClient {

    @GetMapping("api/v1/courses/{course_id}/flashcards/ids")
    ResponseEntity<Set<Long>> getFlashcardIdsByCourseId(@PathVariable("course_id") Long courseId,
                                                        @RequestHeader String Authorization);
}
