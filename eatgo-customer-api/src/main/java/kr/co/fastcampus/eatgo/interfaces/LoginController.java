package kr.co.fastcampus.eatgo.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.interfaces.dto.LoginRequestDto;
import kr.co.fastcampus.eatgo.util.JwtProvider;

@RestController
public class LoginController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public LoginController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        User user = userService.authenticate(loginRequestDto.getEmail(),
            loginRequestDto.getPassword());
        String token = jwtProvider.createToken(user.getId(), user.getName());
        return ResponseEntity.ok("{\"accessToken\":\"" + token + "\"}");
    }
}
