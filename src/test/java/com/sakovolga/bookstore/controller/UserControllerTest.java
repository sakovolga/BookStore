package com.sakovolga.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakovolga.bookstore.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/drop-tables.sql")
@Sql("/create-tables.sql")
@Sql("/insert_test_data.sql")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUserTest() {
    }

    @Test
    @WithUserDetails(value = "jurij@mail.com")
    void getUserByIdTest() throws Exception {
        String id = "1";

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto actualUserDto = objectMapper.readValue(result, UserDto.class);
        UserDto expectedUserDto = getUserDto();

        Assertions.assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    @WithUserDetails(value = "jurij@mail.com")
    void getUserByEmailTest() throws Exception {
        String email = "petr@mail.com";

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/email/" + email))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto actualUserDto = objectMapper.readValue(result, UserDto.class);
        UserDto expectedUserDto = getUserDto();

        Assertions.assertEquals(expectedUserDto, actualUserDto);
    }

    private UserDto getUserDto(){
        UserDto userDto = new UserDto();
        userDto.setFirstName("Petr");
        userDto.setSecondName("Petrov");
        userDto.setId("1");
        userDto.setEmail("petr@mail.com");
        userDto.setPassword("*****");
        return userDto;
    }
}