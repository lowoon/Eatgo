package kr.co.fastcampus.eatgo.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.interfaces.dto.LoginRequestDto;

@RestController
public class SessionController {

    private final UserService userService;

    public SessionController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/session")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        userService.authenticate(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        return ResponseEntity.ok().header("token", "aaa").build();
    }
}
