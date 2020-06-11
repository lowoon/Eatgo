package kr.co.fastcampus.eatgo.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kr.co.fastcampus.eatgo.domain.Region;
import kr.co.fastcampus.eatgo.domain.RegionRepository;

class RegionServiceTest {

    private RegionService regionService;

    @Mock
    private RegionRepository regionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        regionService = new RegionService(regionRepository);
    }

    @Test
    void getRegions() {
        List<Region> mockRegions = new ArrayList<>();
        mockRegions.add(Region.builder().name("Seoul").build());

        when(regionRepository.findAll()).thenReturn(mockRegions);

        List<Region> regions = regionService.getRegions();

        Region region = regions.get(0);
        assertThat(region.getName()).isEqualTo("Seoul");
    }

    @Test
    void addRegion() {
        Region mockRegion = Region.builder().name("Seoul").build();

        when(regionRepository.save(any(Region.class))).thenReturn(mockRegion);

        Region region = regionService.addRegion(mockRegion);

        assertThat(region.getName()).isEqualTo("Seoul");
    }
}