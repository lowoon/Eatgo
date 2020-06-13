package kr.co.fastcampus.eatgo.interfaces.exception;

public class NotExistedEmailException extends RuntimeException {
    public NotExistedEmailException(String email) {
        super("등록되지 않은 이메일입니다 : " + email);
    }
}
