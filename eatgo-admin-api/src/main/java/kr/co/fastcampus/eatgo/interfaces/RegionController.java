package kr.co.fastcampus.eatgo.interfaces;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.fastcampus.eatgo.application.RegionService;
import kr.co.fastcampus.eatgo.domain.Region;

@RestController
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/regions")
    public List<Region> list() {
        List<Region> regions = regionService.getRegions();
        return regions;
    }

    @PostMapping("/regions")
    public ResponseEntity<Void> create(@RequestBody Region resource) throws URISyntaxException {
        Region region = regionService.addRegion(Region.builder()
            .name(resource.getName())
            .build());

        URI location = new URI("/regions/" + region.getId());
        return ResponseEntity.created(location).build();
    }
}
