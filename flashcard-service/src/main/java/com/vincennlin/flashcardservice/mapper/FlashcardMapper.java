package com.vincennlin.flashcardservice.mapper;

import com.vincennlin.flashcardservice.entity.flashcard.Flashcard;
import com.vincennlin.flashcardservice.entity.review.ReviewInfo;
import com.vincennlin.flashcardservice.entity.review.ReviewState;
import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.flashcardservice.payload.review.dto.ReviewInfoDto;
import com.vincennlin.flashcardservice.payload.review.dto.ReviewStateDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class FlashcardMapper {

    private final ModelMapper modelMapper;

    public FlashcardMapper() {
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Converter<ReviewInfo, ReviewInfoDto> reviewInfoConverter = context ->
                context.getSource() == null ? null : modelMapper.map(context.getSource(), ReviewInfoDto.class);
        modelMapper.addConverter(reviewInfoConverter);

        Converter< ReviewStateDto, ReviewState> reviewStateConverter = context ->
                context.getSource() == null ? null : modelMapper.map(context.getSource(), ReviewState.class);
        modelMapper.addConverter(reviewStateConverter);
    }

    public FlashcardDto mapToDto(Flashcard flashcard) {
        return modelMapper.map(flashcard, flashcard.getType().getFlashcardDtoClass());
    }

    public Flashcard mapToEntity(FlashcardDto flashcardDto) {
        return modelMapper.map(flashcardDto, flashcardDto.getType().getFlashcardEntityClass());
    }

    public ReviewStateDto mapReviewStateToDto(ReviewState reviewState) {
        return modelMapper.map(reviewState, ReviewStateDto.class);
    }
}
