package com.vincennlin.flashcardservice.mapper;

import com.vincennlin.flashcardservice.entity.flashcard.Flashcard;
import com.vincennlin.flashcardservice.entity.review.ReviewInfo;
import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.flashcardservice.payload.review.dto.ReviewInfoDto;
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

        Converter<ReviewInfo, ReviewInfoDto> reviewConverter = context ->
                context.getSource() == null ? null : modelMapper.map(context.getSource(), ReviewInfoDto.class);

        modelMapper.addConverter(reviewConverter);
    }

    public FlashcardDto mapToDto(Flashcard flashcard) {
        return modelMapper.map(flashcard, flashcard.getType().getFlashcardDtoClass());
    }

    public Flashcard mapToEntity(FlashcardDto flashcardDto) {
        return modelMapper.map(flashcardDto, flashcardDto.getType().getFlashcardEntityClass());
    }
}
