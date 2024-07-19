package com.vincennlin.userservice.service.impl;

import com.vincennlin.userservice.entity.Role;
import com.vincennlin.userservice.entity.User;
import com.vincennlin.userservice.exception.UserNotFoundException;
import com.vincennlin.userservice.exception.WebAPIException;
import com.vincennlin.userservice.payload.*;
import com.vincennlin.userservice.repository.RoleRepository;
import com.vincennlin.userservice.repository.UserRepository;
import com.vincennlin.userservice.security.FlashcardwebUserDetails;
import com.vincennlin.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper;

    @Override
    public RegisterResponse register(RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Username is already taken!");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Email is already taken!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        user.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));

        User newUser = userRepository.save(user);

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setMessage("User registered successfully!");
        registerResponse.setAccountInfo(modelMapper.map(newUser, AccountInfoDto.class));

        return registerResponse;
    }

    @Override
    public UserDto getUserDetailsByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new WebAPIException(HttpStatus.NOT_FOUND, "User not found with username: " + username));

        return modelMapper.map(user, UserDto.class);
    }

    @PostAuthorize("returnObject.id == principal or hasAuthority('ADVANCED')")
    @Override
    public UserDto getUserByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new WebAPIException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Collection<Role> roles = user.getRoles();

        roles.forEach((role) -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));

            role.getAuthorities().forEach((authority) -> {
                authorities.add(new SimpleGrantedAuthority(authority.getName()));
            });
        });

        return new FlashcardwebUserDetails(user.getUsername(), user.getEmail(),
                user.getPassword(), authorities, user.getId());
    }

    @Override
    public AccountInfoDto getCurrentAccountInfo() {

        Long currentUserId = getCurrentUserId();

        User user = userRepository.findById(currentUserId).orElseThrow(() ->
                new UserNotFoundException(currentUserId));

        return modelMapper.map(user, AccountInfoDto.class);
    }

    @Override
    public List<AccountInfoDto> getAllUsers() {

        return userRepository.findAll().stream().map(user ->
                modelMapper.map(user, AccountInfoDto.class)).toList();
    }

    @Transactional
    @Override
    public UpdateAccountInfoResponse updateAccountInfo(AccountInfoDto accountInfoDto) {

        if (userRepository.existsByUsername(accountInfoDto.getUsername())) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Username is already taken!");
        }

        if (userRepository.existsByEmail(accountInfoDto.getEmail())) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Email is already taken!");
        }

        Long currentUserId = getCurrentUserId();

        authorizeByUserId(currentUserId);

        User user = userRepository.findById(currentUserId).orElseThrow(() ->
                new UserNotFoundException(currentUserId));

        if (accountInfoDto.getName() != null)
            user.setName(accountInfoDto.getName());
        if (accountInfoDto.getUsername() != null)
            user.setUsername(accountInfoDto.getUsername());
        if (accountInfoDto.getEmail() != null)
            user.setEmail(accountInfoDto.getEmail());
        if (accountInfoDto.getPassword() != null)
            user.setPassword(passwordEncoder.encode(accountInfoDto.getPassword()));

        User updatedUser = userRepository.save(user);

        UpdateAccountInfoResponse updateAccountInfoResponse = new UpdateAccountInfoResponse();

        if (accountInfoDto.getUsername() != null || accountInfoDto.getEmail() != null || accountInfoDto.getPassword() != null) {
            updateAccountInfoResponse.setMessage("User updated successfully! Please login again.");
        } else {
            updateAccountInfoResponse.setMessage("User updated successfully!");
        }
        updateAccountInfoResponse.setAccountInfo(modelMapper.map(updatedUser, AccountInfoDto.class));

        return updateAccountInfoResponse;
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private Long getCurrentUserId() {
        return Long.parseLong(getAuthentication().getPrincipal().toString());
    }

    private void authorizeByUserId(Long userId) {
        if (!userId.equals(getCurrentUserId())) {
            throw new WebAPIException(HttpStatus.FORBIDDEN, "You are not authorized to perform this action!");
        }
    }
}
