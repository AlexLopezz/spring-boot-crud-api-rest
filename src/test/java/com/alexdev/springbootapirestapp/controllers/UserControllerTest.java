package com.alexdev.springbootapirestapp.controllers;

import com.alexdev.springbootapirestapp.models.User;
import com.alexdev.springbootapirestapp.services.impl.UserService;
import com.alexdev.springbootapirestapp.services.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.engine.UserBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.List;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    static List<User> usersTest;
    static User userTest;
    ObjectMapper objMapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @BeforeAll
    static void init(){
        usersTest= List.of(
                User.builder().id(1L).name("Alex").lastName("Lopez").email("asda@gmail.com").build(),
                User.builder().id(2L).name("Jorge").lastName("Franco").email("dsaf@gmail.com").build()
        );
        userTest = User.builder()
                .id((long)(usersTest.size()+1))
                .name("Florencia")
                .lastName("Lopez")
                .email("afgh@gmail.com")
                .build();
    }

    @Test
    void shouldFindAllUser() throws Exception {
        when(userService.findAll())
                .thenReturn(usersTest);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", Matchers.is("Alex")))
                .andExpect(jsonPath("$[1].name", Matchers.is("Jorge")))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void shouldFindUserById() throws Exception {
        when(userService.findById(1))
                .thenReturn(usersTest.get(0));

        doGetRequest("/users/1")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("Alex")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Lopez")))
                .andExpect(jsonPath("$.email", Matchers.is("asda@gmail.com")));
    }

    @Test
    void shouldSaveUser() throws Exception {
        var json = objMapper.writeValueAsString(userTest);

        when(userService.save(any(User.class)))
                .thenReturn(userTest);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().json(json))
                .andExpect(jsonPath("$.name").value(userTest.getName()))
                .andExpect(jsonPath("$.lastName").value(userTest.getLastName()))
                .andExpect(jsonPath("$.email").value(userTest.getEmail()));
    }
    @Test
    void shouldUpdateUserById() throws Exception {
        User userUpdated = userTest;
        userUpdated.setName("Ricardo");
        userUpdated.setEmail("asdsada@gmail.com");

        when(userService.save(any(User.class)))
                .thenReturn(userUpdated);

        String jsonContent = objMapper.writeValueAsString(userUpdated);

        mockMvc.perform(put("/users")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json(jsonContent))
                .andExpect(jsonPath("$.name").value(userUpdated.getName()))
                .andExpect(jsonPath("$.email").value(userUpdated.getEmail()));
    }

    @Test
    void shouldDeleteUserById() throws Exception {
        doNothing()
                .when(userService)
                .deleteById(1);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteById(1);
    }

    private ResultActions doGetRequest(String url) throws Exception {
        return mockMvc.perform(get(url));
    }
}
