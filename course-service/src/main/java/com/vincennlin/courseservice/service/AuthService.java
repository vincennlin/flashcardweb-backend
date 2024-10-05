package com.vincennlin.courseservice.service;

public interface AuthService {

    Long getCurrentUserId();

    void authorizeOwnership(Long ownerId);

    Boolean containsAuthority(String authorityName);

    String getAuthorization();
}
