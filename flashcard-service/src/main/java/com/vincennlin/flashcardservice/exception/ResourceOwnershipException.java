package com.vincennlin.flashcardservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ResourceOwnershipException extends RuntimeException {

    private Long userId;
    private String resourceName;

    public ResourceOwnershipException(Long userId, String resourceName) {
        super(String.format("Current user with id: '%d' cannot access resource: '%s' owned by another user", userId, resourceName));
        this.userId = userId;
        this.resourceName = resourceName;
    }
}
