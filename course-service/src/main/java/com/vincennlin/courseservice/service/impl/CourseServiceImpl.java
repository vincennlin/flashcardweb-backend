package com.vincennlin.courseservice.service.impl;

import com.vincennlin.courseservice.client.FlashcardServiceClient;
import com.vincennlin.courseservice.client.NoteServiceClient;
import com.vincennlin.courseservice.entity.Course;
import com.vincennlin.courseservice.exception.ResourceNotFoundException;
import com.vincennlin.courseservice.exception.WebAPIException;
import com.vincennlin.courseservice.mapper.CourseMapper;
import com.vincennlin.courseservice.payload.course.dto.CourseDto;
import com.vincennlin.courseservice.payload.course.page.CoursePageResponse;
import com.vincennlin.courseservice.payload.flashcard.FlashcardDto;
import com.vincennlin.courseservice.payload.note.CreateNoteRequest;
import com.vincennlin.courseservice.payload.note.NoteClientResponse;
import com.vincennlin.courseservice.payload.request.CreateCourseRequest;
import com.vincennlin.courseservice.payload.request.FlashcardIdsRequest;
import com.vincennlin.courseservice.payload.request.CopyFlashcardsToDeckRequest;
import com.vincennlin.courseservice.repository.CourseRepository;
import com.vincennlin.courseservice.service.AuthService;
import com.vincennlin.courseservice.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;

    private final NoteServiceClient noteServiceClient;
    private final FlashcardServiceClient flashcardServiceClient;

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

    @Transactional
    @Override
    public CourseDto createCourse(CreateCourseRequest request) {

        Long userId = authService.getCurrentUserId();

        Course course = new Course(request.getName(), userId);

        Course savedCourse = courseRepository.save(course);

        return courseMapper.mapToDto(savedCourse);
    }

    @Transactional
    @Override
    public CourseDto updateCourse(Long courseId, CourseDto courseDto) {

        Course course = getCourseEntityById(courseId);

        authService.authorizeOwnership(course.getCreatorId());

        course.setName(courseDto.getName());

        Course updatedCourse = courseRepository.save(course);

        return courseMapper.mapToDto(updatedCourse);
    }

    @Transactional
    @Override
    public void deleteCourse(Long courseId) {

        Course course = getCourseEntityById(courseId);

        authService.authorizeOwnership(course.getCreatorId());

        courseRepository.delete(course);
    }

    @Transactional
    @Override
    public CourseDto enrollCourse(Long courseId) {

        Course course = getCourseEntityById(courseId);

        Long userId = authService.getCurrentUserId();

        if (course.containsUser(userId)) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "User already enrolled in course");
        }

        course.addUser(userId);

        Course updatedCourse = courseRepository.save(course);

        return courseMapper.mapToDto(updatedCourse);
    }

    @Transactional
    @Override
    public CourseDto leaveCourse(Long courseId) {

        Course course = getCourseEntityById(courseId);

        Long userId = authService.getCurrentUserId();

        if (!course.containsUser(userId)) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "User not enrolled in course");
        }

        course.removeUser(userId);

        Course updatedCourse = courseRepository.save(course);

        return courseMapper.mapToDto(updatedCourse);
    }

    @Transactional
    @Override
    public CourseDto addFlashcardsToCourse(Long courseId, FlashcardIdsRequest request) {

        Course course = getCourseEntityById(courseId);

        List<Long> flashcardIdsToAdd = flashcardServiceClient.getFlashcardIdsByCurrentUserIdAndIds(request, authService.getAuthorization()).getBody();

        course.addFlashcardIds(flashcardIdsToAdd);

        Course updatedCourse = courseRepository.save(course);

        return courseMapper.mapToDto(updatedCourse);
    }

    @Transactional
    @Override
    public CourseDto removeFlashcardsFromCourse(Long courseId, FlashcardIdsRequest request) {

        Course course = getCourseEntityById(courseId);

        List<Long> flashcardIdsToRemove = flashcardServiceClient.getFlashcardIdsByCurrentUserIdAndIds(request, authService.getAuthorization()).getBody();

        if (flashcardIdsToRemove == null || flashcardIdsToRemove.isEmpty()) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Flashcards not found");
        }

        course.removeFlashcardIds(flashcardIdsToRemove);

        Course updatedCourse = courseRepository.save(course);

        return courseMapper.mapToDto(updatedCourse);
    }

    @Transactional
    @Override
    public List<FlashcardDto> copyFlashcardsToDeck(Long courseId, CopyFlashcardsToDeckRequest request) {

        Course course = getCourseEntityById(courseId);

        List<Long> flashcardIds = request.getFlashcardIds();
        flashcardIds.forEach(flashcardId -> {
            if (!course.getFlashcardIds().contains(flashcardId)) {
                throw new WebAPIException(HttpStatus.BAD_REQUEST, "Flashcard with id " + flashcardId + " not found in course");
            }
        });

        Long deckId = request.getDeckId();

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setSummary("Flashcards copied from course with id " + courseId + " to deck with id " + deckId);
        createNoteRequest.setContent("This note is automatically created when copying flashcards from course with id " + courseId + " to deck with id " + deckId);

        NoteClientResponse noteClientResponse = noteServiceClient.createNote(deckId, createNoteRequest, authService.getAuthorization()).getBody();
        Long noteId = noteClientResponse.getId();

        return flashcardServiceClient.copyFlashcardsToNote(noteId, flashcardIds, authService.getAuthorization()).getBody();
    }

    private Course getCourseEntityById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId.toString()));
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
