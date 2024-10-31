package com.vincennlin.flashcardservice.service.impl;

import com.vincennlin.flashcardservice.client.AiServiceClient;
import com.vincennlin.flashcardservice.client.CourseServiceClient;
import com.vincennlin.flashcardservice.client.NoteServiceClient;
import com.vincennlin.flashcardservice.entity.review.ReviewInfo;
import com.vincennlin.flashcardservice.entity.tag.Tag;
import com.vincennlin.flashcardservice.mapper.FlashcardMapper;
import com.vincennlin.flashcardservice.payload.deck.FlashcardCountInfo;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.*;
import com.vincennlin.flashcardservice.payload.flashcard.evaluate.EvaluateShortAnswerRequest;
import com.vincennlin.flashcardservice.payload.flashcard.evaluate.EvaluateShortAnswerResponse;
import com.vincennlin.flashcardservice.payload.flashcard.page.FlashcardPageResponse;
import com.vincennlin.flashcardservice.payload.flashcard.type.FlashcardType;
import com.vincennlin.flashcardservice.entity.flashcard.Flashcard;
import com.vincennlin.flashcardservice.entity.flashcard.impl.*;
import com.vincennlin.flashcardservice.exception.FlashcardTypeException;
import com.vincennlin.flashcardservice.exception.ResourceNotFoundException;
import com.vincennlin.flashcardservice.exception.ResourceOwnershipException;
import com.vincennlin.flashcardservice.exception.WebAPIException;
import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.flashcardservice.repository.FlashcardRepository;
import com.vincennlin.flashcardservice.repository.OptionRepository;
import com.vincennlin.flashcardservice.repository.TagRepository;
import com.vincennlin.flashcardservice.service.FlashcardService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Service
public class FlashcardServiceImpl implements FlashcardService {

    private static final Logger logger = LoggerFactory.getLogger(FlashcardServiceImpl.class);

    private final FlashcardMapper flashcardMapper;

    private final FlashcardRepository flashcardRepository;
    private final OptionRepository optionRepository;
    private final TagRepository tagRepository;

    private final NoteServiceClient noteServiceClient;
    private final AiServiceClient aiServiceClient;
    private final CourseServiceClient courseServiceClient;

    @Override
    public FlashcardPageResponse getFlashcardsByDeckId(Long deckId, Pageable pageable) {

        authorizeOwnershipByDeckId(deckId);

        List<Long> noteIds = getNoteIdsByDeckId(deckId);

        return getFlashcardPageResponse(flashcardRepository.findByNoteIdIn(noteIds, pageable));
    }

    @Override
    public FlashcardPageResponse getFlashcardsByNoteId(Long noteId, Pageable pageable) {

        authorizeOwnershipByNoteId(noteId);

        Page<Flashcard> pageOfFlashcards = flashcardRepository.findByNoteId(noteId, pageable);

        return getFlashcardPageResponse(pageOfFlashcards);
    }

    @Override
    public FlashcardDto getFlashcardById(Long flashcardId) {

        return flashcardMapper.mapToDto(getFlashcardEntityById(flashcardId));
    }

    @Override
    public FlashcardPageResponse getFlashcardsByTagNames(List<String> tagNames, Pageable pageable) {

        List<Tag> tagEntities = tagNames.stream().map(tagName ->
                tagRepository.findByTagNameAndUserId(tagName, getCurrentUserId()).orElseThrow(() ->
                        new ResourceNotFoundException("Tag", "tagName", tagName))).toList();

        return getFlashcardPageResponse(flashcardRepository.findByTags(tagEntities, pageable));
    }

    @Override
    public Flashcard getFlashcardEntityById(Long flashcardId) {

        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId.toString()));

        authorizeOwnershipByFlashcardOwnerId(flashcard.getUserId());

        return flashcard;
    }

    @Override
    public FlashcardCountInfo getFlashcardCountInfo() {

        List<Object[]> totalCountResults = flashcardRepository.findNoteIdAndFlashcardCountByUserId(getCurrentUserId());

        Map<Long, Integer> noteIdTotalFlashcardCountMap = new HashMap<>();

        for (Object[] result : totalCountResults) {
            Long noteId = (Long) result[0];
            Long count = (Long) result[1];
            noteIdTotalFlashcardCountMap.put(noteId, count.intValue());
        }

        List<Object[]> reviewCountResults = flashcardRepository.findNoteIdAndFlashcardCountByUserIdAndNextReviewPast(getCurrentUserId());

        Map<Long, Integer> noteIdReviewFlashcardCountMap = new HashMap<>();

        for (Object[] result : reviewCountResults) {
            Long noteId = (Long) result[0];
            Long count = (Long) result[1];
            noteIdReviewFlashcardCountMap.put(noteId, count.intValue());
        }

        FlashcardCountInfo flashcardCountInfo = new FlashcardCountInfo();
        flashcardCountInfo.setNoteIdTotalFlashcardCountMap(noteIdTotalFlashcardCountMap);
        flashcardCountInfo.setNoteIdReviewFlashcardCountMap(noteIdReviewFlashcardCountMap);

        return flashcardCountInfo;
    }

    @Override
    public FlashcardPageResponse findFlashcardsByKeyword(String keyword, Pageable pageable) {

        Long userId = getCurrentUserId();

        return getFlashcardPageResponse(flashcardRepository.findByUserIdAndContentContaining(userId, keyword, pageable));
    }

    @Override
    public FlashcardPageResponse findFlashcardsByDeckIdAndKeyword(Long deckId, String keyword, Pageable pageable) {

        authorizeOwnershipByDeckId(deckId);

        List<Long> noteIds = getNoteIdsByDeckId(deckId);

        return getFlashcardPageResponse(flashcardRepository.findByNoteIdInAndContentContaining(noteIds, keyword, pageable));
    }

    @Override
    public List<Long> getFlashcardIdsByUserIdAndIds(List<Long> flashcardIds) {

        Long userId = getCurrentUserId();

        return flashcardRepository.getIdsByUserIdAndIdIn(userId, flashcardIds);
    }

    @Override
    public FlashcardPageResponse getFlashcardsByCourseId(Long courseId, Pageable pageable) {

        Set<Long> flashcardIds = courseServiceClient.getFlashcardIdsByCourseId(courseId, getAuthorization()).getBody();

        return getFlashcardPageResponse(flashcardRepository.findByIdIn(flashcardIds, pageable));
    }

    @Transactional
    @Override
    public FlashcardDto createFlashcard(Long noteId, FlashcardDto flashcardDto) {

        authorizeOwnershipByNoteId(noteId);

        flashcardDto.setUserId(getCurrentUserId());
        flashcardDto.setNoteId(noteId);

        Flashcard flashcard;

        if (flashcardDto.getType() == FlashcardType.FILL_IN_THE_BLANK) {
            flashcard = mapFillInTheBlankFlashcardToEntity((FillInTheBlankFlashcardDto) flashcardDto);
        } else if (flashcardDto.getType() == FlashcardType.MULTIPLE_CHOICE) {
            flashcard = mapMultipleChoiceFlashcardToEntity((MultipleChoiceFlashcardDto) flashcardDto);
        } else {
            flashcard = flashcardMapper.mapToEntity(flashcardDto);
        }

        ReviewInfo reviewInfo = new ReviewInfo();

        reviewInfo.setFlashcard(flashcard);
        flashcard.setReviewInfo(reviewInfo);

        Flashcard newFlashcard = flashcardRepository.save(flashcard);

        return flashcardMapper.mapToDto(newFlashcard);
    }

    @Transactional
    public Flashcard mapFillInTheBlankFlashcardToEntity(FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto) {

        FillInTheBlankFlashcard fillInTheBlankFlashcard = (FillInTheBlankFlashcard) flashcardMapper.mapToEntity(fillInTheBlankFlashcardDto);

        fillInTheBlankFlashcard.getInBlankAnswers().forEach(inBlankAnswers -> {
            inBlankAnswers.setFlashcard(fillInTheBlankFlashcard);
            inBlankAnswers.setId(null);
        });

        return fillInTheBlankFlashcard;
    }

    @Transactional
    public Flashcard mapMultipleChoiceFlashcardToEntity(MultipleChoiceFlashcardDto multipleChoiceFlashcardDto) {

        if (multipleChoiceFlashcardDto.getAnswerIndex() != null && multipleChoiceFlashcardDto.getAnswerIndex() > multipleChoiceFlashcardDto.getOptions().size()) {
            throw new IllegalArgumentException("Answer index is out of range of options");
        }

        MultipleChoiceFlashcard multipleChoiceFlashcard = (MultipleChoiceFlashcard) multipleChoiceFlashcardDto.mapToEntity();

        List<Option> options = multipleChoiceFlashcard.getOptions();
        options.forEach(option -> option.setId(null));

        multipleChoiceFlashcard.setAnswerOption(null);

        MultipleChoiceFlashcard savedFlashcard = flashcardRepository.save(multipleChoiceFlashcard);

        options.forEach(option -> option.setFlashcard(savedFlashcard));

        List<Option> savedOptions = optionRepository.saveAll(options);

        if (multipleChoiceFlashcardDto.getAnswerIndex() != null) {
            savedFlashcard.setAnswerOption(savedOptions.get(multipleChoiceFlashcardDto.getAnswerIndex() - 1));
        } else {
            Option answerOption = null;
            for (Option option : savedOptions) {
                if (option.getText().equals(multipleChoiceFlashcardDto.getAnswerOption().getText())) {
                    answerOption = option;
                    break;
                }
            }
            if (answerOption == null) {
                throw new IllegalArgumentException("Answer option not found in options");
            }
            savedFlashcard.setAnswerOption(answerOption);
        }

        return flashcardRepository.save(savedFlashcard);
    }


    @Transactional
    @Override
    public List<FlashcardDto> createFlashcards(Long noteId, List<FlashcardDto> flashcardDtoList) {

        return flashcardDtoList.stream().map(flashcardDto ->
                createFlashcard(noteId, flashcardDto)).toList();
    }

    @Transactional
    @Override
    public List<FlashcardDto> copyFlashcardsToNote(Long noteId, List<Long> flashcardIds) {

        authorizeOwnershipByNoteId(noteId);

        List<FlashcardDto> flashcardDtoList = flashcardRepository.findByIdIn(flashcardIds)
                .stream().map(flashcard -> {
                    FlashcardDto flashcardDto = flashcardMapper.mapToDto(flashcard);
                    flashcardDto.setId(null);
                    resetRelatedEntityIds(flashcardDto);
                    return flashcardDto;
                }).toList();

        return createFlashcards(noteId, flashcardDtoList);
    }

    private void resetRelatedEntityIds(FlashcardDto flashcardDto) {
        if (flashcardDto instanceof MultipleChoiceFlashcardDto) {
            MultipleChoiceFlashcardDto mcFlashcardDto = (MultipleChoiceFlashcardDto) flashcardDto;
            mcFlashcardDto.getOptions().forEach(option -> option.setId(null));
            if (mcFlashcardDto.getAnswerOption() != null) {
                mcFlashcardDto.getAnswerOption().setId(null);
            }
        } else if (flashcardDto instanceof FillInTheBlankFlashcardDto) {
            FillInTheBlankFlashcardDto fitbFlashcardDto = (FillInTheBlankFlashcardDto) flashcardDto;
            fitbFlashcardDto.getInBlankAnswers().forEach(answer -> answer.setId(null));
        }
    }

    @Override
    public FlashcardDto updateFlashcard(Long flashcardId, FlashcardDto flashcardDto) {

        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId.toString()));

        authorizeOwnershipByFlashcardOwnerId(flashcard.getUserId());

        if (flashcardDto.getType() != flashcard.getType()) {
            throw new FlashcardTypeException(flashcardId, flashcard.getType(), flashcardDto.getType());
        }

        flashcardDto.setId(flashcard.getId());
        flashcardDto.setUserId(flashcard.getUserId());
        flashcardDto.setNoteId(flashcard.getNoteId());

        Flashcard newFlashcard = null;

        if (flashcardDto.getType() == FlashcardType.FILL_IN_THE_BLANK) {
            newFlashcard = updateFillInTheBlankFlashcard((FillInTheBlankFlashcard) flashcard,
                                                            (FillInTheBlankFlashcard) flashcardDto.mapToEntity());
        } else if (flashcardDto.getType() == FlashcardType.MULTIPLE_CHOICE) {
            newFlashcard = updateMultipleChoiceFlashcard((MultipleChoiceFlashcard) flashcard,
                                                            (MultipleChoiceFlashcardDto) flashcardDto);
        } else {
            newFlashcard = flashcardDto.mapToEntity();
        }

        Flashcard updatedFlashcard = flashcardRepository.save(newFlashcard);

        return flashcardMapper.mapToDto(updatedFlashcard);
    }

    public Flashcard updateFillInTheBlankFlashcard(FillInTheBlankFlashcard flashcard,
                                                   FillInTheBlankFlashcard newFlashcard) {

        List<InBlankAnswer> inBlankAnswers = flashcard.getInBlankAnswers();
        int blankAnswerSize = inBlankAnswers.size();

        if (newFlashcard.getInBlankAnswers().size() != blankAnswerSize) {
            throw new IllegalArgumentException("Number of blank answers cannot be changed");
        }

        for (int i = 0; i < blankAnswerSize; i++) {
            inBlankAnswers.get(i).setText(newFlashcard.getInBlankAnswers().get(i).getText());
            inBlankAnswers.get(i).setFlashcard(flashcard);
        }

        newFlashcard.setInBlankAnswers(inBlankAnswers);

        return newFlashcard;
    }

    public Flashcard updateMultipleChoiceFlashcard(MultipleChoiceFlashcard flashcard,
                                                   MultipleChoiceFlashcardDto flashcardDto) {

        List<Option> options = flashcard.getOptions();
        int optionSize = options.size();

        if (flashcardDto.getOptions().size() != optionSize) {
            throw new IllegalArgumentException("Number of options cannot be changed");
        } else if (flashcardDto.getAnswerIndex() > optionSize) {
            throw new IllegalArgumentException("Answer index is out of range of options");
        }

        MultipleChoiceFlashcard newFlashcard = (MultipleChoiceFlashcard) flashcardDto.mapToEntity();

        for (int i = 0; i < optionSize; i++) {
            options.get(i).setText(flashcardDto.getOptions().get(i).getText());
            options.get(i).setFlashcard(newFlashcard);
        }

        List<Option> savedOptions = optionRepository.saveAll(options);
        newFlashcard.setOptions(savedOptions);
        newFlashcard.setAnswerOption(savedOptions.get(flashcardDto.getAnswerIndex() - 1));

        return newFlashcard;
    }

    @Transactional
    @Override
    public void deleteFlashcardById(Long flashcardId) {

        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId.toString()));

        authorizeOwnershipByFlashcardOwnerId(flashcard.getUserId());

        flashcardRepository.delete(flashcard);
    }

    @Transactional
    @Override
    public void deleteFlashcardsByNoteId(Long noteId) {

        authorizeOwnershipByNoteId(noteId);

        flashcardRepository.deleteByNoteId(noteId);
    }

    @Transactional
    @Override
    public void deleteFlashcardsByDeckId(Long deckId) {

        authorizeOwnershipByDeckId(deckId);

        List<Long> noteIds = getNoteIdsByDeckId(deckId);

        flashcardRepository.deleteByNoteIdIn(noteIds);
    }

    @Override
    public EvaluateShortAnswerResponse evaluateShortAnswerByFlashcardId(Long flashcardId, EvaluateShortAnswerRequest request) {

        if (flashcardId != null) {

            Flashcard flashcard = getFlashcardEntityById(flashcardId);

            if (flashcard.getType() != FlashcardType.SHORT_ANSWER) {
                throw new FlashcardTypeException(flashcardId, flashcard.getType(), FlashcardType.SHORT_ANSWER);
            }

            ShortAnswerFlashcard shortAnswerFlashcard = (ShortAnswerFlashcard) flashcard;
            request.setQuestion(shortAnswerFlashcard.getQuestion());
            request.setAnswer(shortAnswerFlashcard.getShortAnswer());
        }

        ResponseEntity<EvaluateShortAnswerResponse> response = null;

        try {
            response = aiServiceClient.evaluateShortAnswer(request, getAuthorization());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new WebAPIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }

        return response.getBody();
    }

    @Override
    public Long getCurrentUserId() {
        return Long.parseLong(getAuthentication().getPrincipal().toString());
    }

    private String getAuthorization() {
        return getAuthentication().getCredentials().toString();
    }

    private void authorizeOwnershipByDeckId(Long deckId) {
        if (!isDeckOwner(deckId) && !containsAuthority("ADVANCED")) {
            throw new ResourceOwnershipException(getCurrentUserId(), "Deck");
        }
    }

    private void authorizeOwnershipByNoteId(Long noteId) {
        if (!isNoteOwner(noteId) && !containsAuthority("ADVANCED")) {
            throw new ResourceOwnershipException(getCurrentUserId(), "Note");
        }
    }

    private void authorizeOwnershipByFlashcardOwnerId(Long flashcardOwnerId) {
        if (flashcardOwnerId != getCurrentUserId() && !containsAuthority("ADVANCED")) {
            throw new ResourceOwnershipException(getCurrentUserId(), "Flashcard");
        }
    }

    private Boolean containsAuthority(String authorityName) {
        return getAuthentication().getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals(authorityName));
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private List<Long> getNoteIdsByDeckId(Long deckId) {
        ResponseEntity<List<Long>> response = null;
        try{
            response = noteServiceClient.getNoteIdsByDeckId(deckId, getAuthorization());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            if (e instanceof FeignException && ((FeignException)e).status() == HttpStatus.NOT_FOUND.value())
                throw new ResourceNotFoundException("Deck", "id", deckId.toString());
            else if (!(e instanceof ResourceNotFoundException))
                throw new WebAPIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
            throw e;
        }
        return response.getBody();
    }

    private Boolean isDeckOwner(Long deckId) {
        ResponseEntity<Boolean> response = null;
        try{
            response = noteServiceClient.isDeckOwner(deckId, getAuthorization());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            if (e instanceof FeignException && ((FeignException)e).status() == HttpStatus.NOT_FOUND.value())
                throw new ResourceNotFoundException("Deck", "id", deckId.toString());
            else if (!(e instanceof ResourceNotFoundException))
                throw new WebAPIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
            throw e;
        }
        return response.getBody();
    }

    private Boolean isNoteOwner(Long noteId) {
        ResponseEntity<Boolean> response = null;
        try{
            response = noteServiceClient.isNoteOwner(noteId, getAuthorization());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            if (e instanceof FeignException && ((FeignException)e).status() == HttpStatus.NOT_FOUND.value())
                throw new ResourceNotFoundException("Note", "id", noteId.toString());
            else if (!(e instanceof ResourceNotFoundException))
                throw new WebAPIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
            throw e;
        }
        return response.getBody();
    }

    private FlashcardPageResponse getFlashcardPageResponse(Page<Flashcard> pageOfFlashcards) {
        List<Flashcard> listOfFlashcards = pageOfFlashcards.getContent();

        List<FlashcardDto> FlashcardDtoList = listOfFlashcards.stream().map(flashcardMapper::mapToDto).toList();

        FlashcardPageResponse flashcardPageResponse = new FlashcardPageResponse();
        flashcardPageResponse.setContent(FlashcardDtoList);
        flashcardPageResponse.setPageNo(pageOfFlashcards.getNumber());
        flashcardPageResponse.setPageSize(pageOfFlashcards.getSize());
        flashcardPageResponse.setTotalElements(pageOfFlashcards.getTotalElements());
        flashcardPageResponse.setTotalPages(pageOfFlashcards.getTotalPages());
        flashcardPageResponse.setLast(pageOfFlashcards.isLast());

        return flashcardPageResponse;
    }
}
