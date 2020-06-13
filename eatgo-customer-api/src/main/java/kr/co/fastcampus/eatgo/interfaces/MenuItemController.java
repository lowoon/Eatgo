package kr.co.fastcampus.eatgo.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.fastcampus.eatgo.application.MenuItemService;
import kr.co.fastcampus.eatgo.domain.MenuItem;

@CrossOrigin
@RestController
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(
        MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping("/restaurants/{restaurantId}/menuItems")
    public List<MenuItem> list(@PathVariable("restaurantId") Long restaurantId) {
        return menuItemService.getMenuItems(restaurantId);
    }

    @PatchMapping("/restaurants/{restaurantId}/menuItems")
    public ResponseEntity<Void> bulkUpdate(@PathVariable("restaurantId") Long restaurantId,
            @RequestBody List<MenuItem> menuItems) {
        menuItemService.bulkUpdate(restaurantId, menuItems);
        return ResponseEntity.noContent().build();
    }
}
