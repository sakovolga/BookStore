package com.sakovolga.bookstore.service.impl;

import com.sakovolga.bookstore.dto.JwtAuthenticationDto;
import com.sakovolga.bookstore.dto.RefreshTokenDto;
import com.sakovolga.bookstore.dto.UserCredentialsDto;
import com.sakovolga.bookstore.dto.UserDto;
import com.sakovolga.bookstore.entity.User;
import com.sakovolga.bookstore.mapper.UserMapper;
import com.sakovolga.bookstore.repository.UserRepository;
import com.sakovolga.bookstore.security.jwt.JWTService;
import com.sakovolga.bookstore.service.UserService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, JWTService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JwtAuthenticationDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        User user = findByCredentials(userCredentialsDto);
        return jwtService.generateAuthToken(user.getEmail());
    }

    @Override
    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            User user = findByEmail(jwtService.getEmailFromToken(refreshToken));
            return jwtService.refreshBaseToken(user.getEmail(), refreshToken);
        }
        throw new  AuthenticationException("Invalid refresh token");
    }

    @Override
    @Transactional
    public UserDto getUserById(Long id) throws ChangeSetPersister.NotFoundException {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @Override
    @Transactional
    public UserDto getUserByEmail(String email) throws ChangeSetPersister.NotFoundException {
        return userMapper.toDto(userRepository.findByEmail(email)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @Override
    public String addUser(UserDto userDto){
        User checkedUser = userRepository.findByEmail(userDto.getEmail()).orElse(null);
        if (checkedUser == null){
            User user = userMapper.toEntity(userDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "User added";
        } else return "User already exists";
    }

    private User findByCredentials(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findByEmail(userCredentialsDto.getEmail());
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            if (passwordEncoder.matches(userCredentialsDto.getPassword(), user.getPassword())){
                return user;
            }
        }
        throw new AuthenticationException("Email or password is not correct");
    }

    private User findByEmail(String email) throws Exception {
        return userRepository.findByEmail(email).orElseThrow(()->
                new Exception(String.format("User with email %s not found", email)));
    }
}
