package kr.co.fastcampus.eatgo.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RegionTest {

    @Test
    void create() {
        Region region = Region.builder()
            .name("서울")
            .build();

        assertThat(region.getName()).isEqualTo("서울");
    }
}