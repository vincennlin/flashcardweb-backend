package com.vincennlin.flashcardwebbackend.payload;

import lombok.Data;

import java.util.List;

@Data
public class NotePageResponse {

    List<NoteDto> content;
    private Integer pageNo;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean last;
}
