package kr.co.fastcampus.eatgo.interfaces;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void create() throws Exception {
        String name = "example";
        String email = "example@example.com";
        String password = "test";

        User user = User.builder().email(email).name(name).pasword(password).build();

        String body = objectMapper.writeValueAsString(user);

        when(userService.registerUser(name, email, password)).thenReturn(User.builder()
            .id(1L)
            .name(name)
            .email(email)
            .level(1L)
            .pasword(password)
            .build());

        mvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().string("location", "/users/1"));
    }
}