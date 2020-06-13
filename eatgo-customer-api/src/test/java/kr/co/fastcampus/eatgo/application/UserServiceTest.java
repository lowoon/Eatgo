package kr.co.fastcampus.eatgo.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import kr.co.fastcampus.eatgo.exception.DuplicatedEmailException;
import kr.co.fastcampus.eatgo.interfaces.exception.NotExistedEmailException;
import kr.co.fastcampus.eatgo.interfaces.exception.WrongPasswordException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, passwordEncoder);
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

    @Test
    void authenticate() {
        String email = "tester@example.com";
        String password = "test";
        String name = "tester";

        User user = User.builder()
            .name(name)
            .email(email)
            .pasword(password)
            .id(1L)
            .level(1L)
            .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        assertThat(userService.authenticate(email, password).getId()).isEqualTo(user.getId());
    }

    @Test
    void authenticateWithWrongEmail() {
        String email = "tester@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.authenticate(email, anyString()))
            .isInstanceOf(NotExistedEmailException.class);
    }

    @Test
    void authenticateWithWrongPassword() {
        String email = "tester@example.com";
        String password = "test";
        String name = "tester";

        User user = User.builder()
            .name(name)
            .email(email)
            .pasword(password)
            .id(1L)
            .level(1L)
            .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("s", password)).thenReturn(false);

        assertThatThrownBy(() -> userService.authenticate(email, "s"))
            .isInstanceOf(WrongPasswordException.class);
    }
}