package com.employee.management.service.impl;

import com.employee.management.entity.User;
import com.employee.management.exception.NotFoundException;
import com.employee.management.mapper.UserMapper;
import com.employee.management.model.LoginRequest;
import com.employee.management.model.LoginResponse;
import com.employee.management.model.UserRequest;
import com.employee.management.model.UserResponse;
import com.employee.management.repository.UserRepository;
import com.employee.management.service.UserService;
import com.employee.management.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponse saveUser(UserRequest request) {
        logger.info("ActionLog.saveUser.start request: {}", request);

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        var user = UserMapper.INSTANCE.modelToEntity(request);
        var response = UserMapper.INSTANCE.entityToModel(userRepository.save(user));

        logger.info("ActionLog.saveUser.end response: {}", response);
        return response;
    }






    @Override
    public LoginResponse login(LoginRequest request) {
        logger.info("ActionLog.login.start request: {}", request);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(),
                request.password()));

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new NotFoundException("User is NotFound is username :" + request.password()));

        String token = jwtService.generateToken(user);

        logger.info("ActionLog.login.end response: {}", token);
        return LoginResponse
                .builder()
                .token(token)
                .build();

    }


}