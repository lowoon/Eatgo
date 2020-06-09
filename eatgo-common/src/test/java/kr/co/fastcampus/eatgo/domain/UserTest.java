package kr.co.fastcampus.eatgo.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void create() {
        User user = User.builder()
            .name("테스터")
            .email("tester@example.com")
            .level(1L)
            .build();

        assertThat(user.getName()).isEqualTo("테스터");
    }

    @Test
    void deactivate() {
        User user = User.builder()
            .name("테스터")
            .email("tester@example.com")
            .level(1L)
            .build();

        user.deactivate();

        assertThat(user.getLevel()).isEqualTo(0L);
    }
}