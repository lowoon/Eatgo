package kr.co.fastcampus.eatgo.interfaces;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
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

    @PostMapping("/users")
    public ResponseEntity<Void> create(@RequestBody User resource) throws URISyntaxException {
        User user = userService.registerUser(resource.getName(), resource.getEmail(), resource.getPasword());

        URI location = new URI("/users/" + user.getId());
        return ResponseEntity.created(location).build();
    }
}
