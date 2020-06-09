package kr.co.fastcampus.eatgo.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;

class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void getUsers() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(User.builder()
            .id(1L)
            .name("테스터")
            .email("tester@example.com")
            .level(1L)
            .build());

        given(userRepository.findAll()).willReturn(mockUsers);

        assertThat(userService.getUsers().get(0).getName()).isEqualTo("테스터");
    }

    @Test
    void addUser() {
        String name = "Administrator";
        String email = "admin@example.com";

        given(userRepository.save(any())).willReturn(User.builder()
            .id(1L)
            .name(name)
            .email(email)
            .level(3L)
            .build());

        User user = userService.addUser(name, email);

        assertThat(user.getName()).isEqualTo(name);
    }

    @Test
    void updateUser() {
        Long id = 1004L;
        String name = "Administrator";
        String email = "admin@example.com";
        Long level = 3L;

        given(userRepository.save(any())).willReturn(User.builder()
            .id(id)
            .name(name)
            .email(email)
            .level(level)
            .build());

        User user = userService.updateUser(id, name, email, level);

        assertThat(user.getName()).isEqualTo(name);
    }

    @Test
    void deactivateUser() {
        given(userRepository.findById(anyLong())).willReturn(Optional.of(User.builder().build()));

        userService.deactivateUser(1004L);

        verify(userRepository).findById(1004L);
    }
}