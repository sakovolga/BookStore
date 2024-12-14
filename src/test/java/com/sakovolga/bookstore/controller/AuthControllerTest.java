package com.sakovolga.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakovolga.bookstore.dto.JwtAuthenticationDto;
import com.sakovolga.bookstore.dto.RefreshTokenDto;
import com.sakovolga.bookstore.dto.UserCredentialsDto;
import com.sakovolga.bookstore.repository.UserRepository;
import com.sakovolga.bookstore.security.jwt.JWTService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/drop-tables.sql")
@Sql("/create-tables.sql")
@Sql("/insert_test_data.sql")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void singInTest() throws Exception {
        String email = "petr@mail.com";
        String password = "12345";
        UserCredentialsDto userCredentialsDto = new UserCredentialsDto();
        userCredentialsDto.setEmail(email);
        userCredentialsDto.setPassword(password);

        String userCredentialsDtoStr = objectMapper.writeValueAsString(userCredentialsDto);

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userCredentialsDtoStr))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JwtAuthenticationDto jwtAuthenticationDto = objectMapper.readValue(result, JwtAuthenticationDto.class);
        String actualEmail = jwtService.getEmailFromToken(jwtAuthenticationDto.getToken());

        Assertions.assertEquals(email, actualEmail);
    }

    @Test
    void refresh() throws Exception {
        String email = "petr@mail.com";
        RefreshTokenDto refreshTokenDto = getRefreshTokenTest(email);
        String refreshJSON = objectMapper.writeValueAsString(refreshTokenDto);

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(refreshJSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JwtAuthenticationDto jwtAuthenticationDto = objectMapper.readValue(result, JwtAuthenticationDto.class);
        String actualEmail = jwtService.getEmailFromToken(jwtAuthenticationDto.getToken());

        Assertions.assertEquals(email, actualEmail);
    }

    private RefreshTokenDto getRefreshTokenTest(String email){
        JwtAuthenticationDto jwtAuthenticationDto = jwtService.generateAuthToken(email);
        RefreshTokenDto refreshTokenDto = new RefreshTokenDto();
        refreshTokenDto.setRefreshToken(jwtAuthenticationDto.getRefreshToken());
        return refreshTokenDto;
    }
}