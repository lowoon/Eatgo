package kr.co.fastcampus.eatgo.interfaces;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.interfaces.exception.NotExistedEmailException;
import kr.co.fastcampus.eatgo.interfaces.exception.WrongPasswordException;
import kr.co.fastcampus.eatgo.util.JwtProvider;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();
    }

    @Test
    void login() throws Exception {
        Long id = 1004L;
        String name = "tester";
        String email = "tester@exmple.com";
        String password = "test";
        String body = "{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}";

        User user = User.builder()
            .id(id)
            .name(name)
            .email(email)
            .pasword(password)
            .level(1L)
            .build();

        when(userService.authenticate(email, password)).thenReturn(user);

        mvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(
                "{\"accessToken\":\"eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsInVzZXJOYW1lIjoidGVzdGVyIn0.bUqxLMPHoRhKqHFqOJp9BkbLn7Ym48k2b2XL2tPUSKU\"}"));
    }

    @Test
    void loginWithWrongPassword() throws Exception {
        String email = "tester@exmple.com";
        String password = "test";
        String body = "{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}";

        when(userService.authenticate(email, password)).thenThrow(WrongPasswordException.class);

        mvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        )
            .andDo(print())
            .andExpect(status().isBadRequest());

        verify(userService).authenticate(email, password);
    }

    @Test
    void loginWithWrongEmail() throws Exception {
        String email = "tester@exmple.com";
        String password = "test";
        String body = "{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}";

        when(userService.authenticate(email, password)).thenThrow(NotExistedEmailException.class);

        mvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        )
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}