package com.vincennlin.courseservice.service.impl;

import com.vincennlin.courseservice.exception.ResourceOwnershipException;
import com.vincennlin.courseservice.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public Long getCurrentUserId() {
        return Long.parseLong(getAuthentication().getPrincipal().toString());
    }

    @Override
    public void authorizeOwnership(Long ownerId) {
        Long currentUserId = getCurrentUserId();
        if (!currentUserId.equals(ownerId) && !containsAuthority("ADVANCED")) {
            throw new ResourceOwnershipException(currentUserId, ownerId);
        }
    }

    @Override
    public Boolean containsAuthority(String authorityName) {
        return getAuthentication().getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals(authorityName));
    }

    @Override
    public String getAuthorization() {
        return getAuthentication().getCredentials().toString();
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
