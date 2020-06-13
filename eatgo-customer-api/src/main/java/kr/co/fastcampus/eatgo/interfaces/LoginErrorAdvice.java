package kr.co.fastcampus.eatgo.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.co.fastcampus.eatgo.interfaces.exception.NotExistedEmailException;
import kr.co.fastcampus.eatgo.interfaces.exception.WrongPasswordException;

@RestControllerAdvice
public class LoginErrorAdvice {

    private static final Logger logger = LoggerFactory.getLogger(LoginErrorAdvice.class);

    @ExceptionHandler(value = {NotExistedEmailException.class, WrongPasswordException.class})
    public ResponseEntity<Void> handleLoginException(RuntimeException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().build();
    }
}
