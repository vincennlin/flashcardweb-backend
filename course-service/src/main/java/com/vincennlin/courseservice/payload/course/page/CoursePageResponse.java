package com.vincennlin.courseservice.payload.course.page;

import com.vincennlin.courseservice.payload.course.dto.CourseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class CoursePageResponse {

    List<CourseDto> content;

    @Schema(
            name = "pageNo",
            description = "目前頁數",
            example = "0"
    )
    private Integer pageNo;

    @Schema(
            name = "pageSize",
            description = "每頁筆記數",
            example = "10"
    )
    private Integer pageSize;

    @Schema(
            name = "totalElements",
            description = "筆記總數",
            example = "100"
    )
    private Long totalElements;

    @Schema(
            name = "totalPages",
            description = "總頁數",
            example = "10"
    )
    private Integer totalPages;

    @Schema(
            name = "last",
            description = "是否為最後一頁",
            example = "true"
    )
    private Boolean last;
}
