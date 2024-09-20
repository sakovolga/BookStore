package com.sakovolga.bookstore.service;

import com.sakovolga.bookstore.dto.JwtAuthenticationDto;
import com.sakovolga.bookstore.dto.RefreshTokenDto;
import com.sakovolga.bookstore.dto.UserCredentialsDto;
import com.sakovolga.bookstore.dto.UserDto;
import org.springframework.data.crossstore.ChangeSetPersister;

import javax.naming.AuthenticationException;

public interface UserService {
    JwtAuthenticationDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException;
    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;
    UserDto getUserById(Long id) throws ChangeSetPersister.NotFoundException;
    UserDto getUserByEmail(String email) throws ChangeSetPersister.NotFoundException;
    String addUser(UserDto user);
}
