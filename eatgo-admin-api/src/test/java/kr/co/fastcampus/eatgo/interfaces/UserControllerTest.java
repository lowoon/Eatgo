package kr.co.fastcampus.eatgo.interfaces;

import static org.hamcrest.core.StringContains.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

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

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();
    }

    @Test
    void list() throws Exception {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(User.builder()
            .id(1L)
            .name("테스터")
            .email("tester@example.com")
            .level(1L)
            .build());

        given(userService.getUsers()).willReturn(mockUsers);

        mvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("\"name\":\"테스터\"")))
            .andDo(print());
    }


    @Test
    void create() throws Exception {
        String email = "admin@example.com";
        String name = "Administrator";

        given(userService.addUser(name, email)).willReturn(User.builder()
            .id(1L)
            .name(name)
            .email(email)
            .build());

        mvc.perform(post("/users")
            .content("{\"name\":\"Administrator\",\"email\":\"admin@example.com\",\"level\":3}")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated())
            .andDo(print());

        verify(userService).addUser(name, email);
    }

    @Test
    void update() throws Exception {
        Long id = 1004L;
        String email = "admin@example.com";
        String name = "Administrator";
        Long level = 3L;

        mvc.perform(patch("/users/1004")
            .content("{\"name\":\"Administrator\",\"email\":\"admin@example.com\",\"level\":3}")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk());

        verify(userService).updateUser(id, name, email, level);
    }

    @Test
    void deactivate() throws Exception {
        mvc.perform(delete("/users/1004"))
            .andDo(print())
            .andExpect(status().isOk());

        verify(userService).deactivateUser(1004L);
    }
}