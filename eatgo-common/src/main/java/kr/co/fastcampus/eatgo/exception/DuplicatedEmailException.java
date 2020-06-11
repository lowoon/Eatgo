package kr.co.fastcampus.eatgo.exception;

public class DuplicatedEmailException extends RuntimeException {

    public DuplicatedEmailException(String email) {
        super("Email is already existed : " + email);
    }
}
