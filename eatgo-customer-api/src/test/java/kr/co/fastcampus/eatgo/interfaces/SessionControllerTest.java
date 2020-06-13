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

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.interfaces.exception.NotExistedEmailException;
import kr.co.fastcampus.eatgo.interfaces.exception.WrongPasswordException;

@WebMvcTest(controllers = SessionController.class)
class SessionControllerTest {

    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();
    }

    @Test
    void login() throws Exception {
        String email = "tester@exmple.com";
        String password = "test";
        String body = "{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}";


        mvc.perform(post("/session")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string("token", "aaa"));

        verify(userService).authenticate(email, password);
    }

    @Test
    void loginWithWrongPassword() throws Exception {
        String email = "tester@exmple.com";
        String password = "test";
        String body = "{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}";

        when(userService.authenticate(email, password)).thenThrow(WrongPasswordException.class);

        mvc.perform(post("/session")
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

        mvc.perform(post("/session")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        )
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}