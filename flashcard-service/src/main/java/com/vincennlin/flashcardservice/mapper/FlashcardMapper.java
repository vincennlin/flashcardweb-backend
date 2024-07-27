package com.vincennlin.flashcardservice.mapper;

import com.vincennlin.flashcardservice.entity.flashcard.AbstractFlashcard;
import com.vincennlin.flashcardservice.entity.review.Review;
import com.vincennlin.flashcardservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.flashcardservice.payload.review.dto.ReviewDto;
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

        Converter<Review, ReviewDto> reviewConverter = context ->
                context.getSource() == null ? null : modelMapper.map(context.getSource(), ReviewDto.class);

        modelMapper.addConverter(reviewConverter);
    }

    public AbstractFlashcardDto mapToDto(AbstractFlashcard flashcard) {
        return modelMapper.map(flashcard, flashcard.getType().getFlashcardDtoClass());
    }

    public AbstractFlashcard mapToEntity(AbstractFlashcardDto flashcardDto) {
        return modelMapper.map(flashcardDto, flashcardDto.getType().getFlashcardEntityClass());
    }
}
