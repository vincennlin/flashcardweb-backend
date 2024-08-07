package com.vincennlin.noteservice.converter;

import com.vincennlin.noteservice.payload.extract.ExtractLanguage;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToExtractLanguageConverter implements Converter<String, ExtractLanguage> {

    @Override
    public ExtractLanguage convert(String source) {
        return ExtractLanguage.fromString(source);
    }
}