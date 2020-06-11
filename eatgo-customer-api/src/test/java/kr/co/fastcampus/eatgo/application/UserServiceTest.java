package kr.co.fastcampus.eatgo.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import kr.co.fastcampus.eatgo.exception.DuplicatedEmailException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void registerUser() {
        userService.registerUser("tester", "tester@example.com", "test");

        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUserWithExistedEmail() {
        String name = "tester";
        String email = "tester@example.com";
        String password = "test";
        User mockUser = User.builder()
            .id(1L)
            .name(name)
            .email(email)
            .pasword(password)
            .level(1L)
            .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        assertThatThrownBy(() -> userService.registerUser(name, email, password))
            .isInstanceOf(DuplicatedEmailException.class);

        verify(userRepository, never()).save(any(User.class));
    }
}