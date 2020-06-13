package kr.co.fastcampus.eatgo.interfaces;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> list() {
        return userService.getUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<Void> create(@RequestBody User resource) throws URISyntaxException {
        User persisted = userService.addUser(resource.getName(), resource.getEmail());
        URI location = new URI("/users/" + persisted.getId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<Void> update(@PathVariable("userId") Long userId, @RequestBody User resource) {
        userService.updateUser(userId, resource.getName(), resource.getEmail(),
            resource.getLevel());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok().build();
    }
}
