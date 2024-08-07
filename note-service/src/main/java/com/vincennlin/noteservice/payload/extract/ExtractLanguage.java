package com.vincennlin.noteservice.payload.extract;

public enum ExtractLanguage implements AbstractExtractLanguage{

    ENG {
        @Override
        public String getTesseractLanguage() {
            return "eng";
        }
    },

    CHI {
        @Override
        public String getTesseractLanguage() {
            return "chi_tra";
        }
    };

    public static ExtractLanguage fromString(String language) {
        for (ExtractLanguage lang : ExtractLanguage.values()) {
            if (lang.name().equalsIgnoreCase(language)) {
                return lang;
            }
        }
        throw new IllegalArgumentException("Unknown language: " + language);
    }
}
