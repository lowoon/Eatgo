package kr.co.fastcampus.eatgo.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import kr.co.fastcampus.eatgo.exception.DuplicatedEmailException;
import kr.co.fastcampus.eatgo.interfaces.exception.NotExistedEmailException;
import kr.co.fastcampus.eatgo.interfaces.exception.WrongPasswordException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicatedEmailException(email);
        }
        String encodedPassword = passwordEncoder.encode(password);
        User user = User.OfCustomer(name, email, encodedPassword);
        return userRepository.save(user);
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new NotExistedEmailException(email));
        if (!passwordEncoder.matches(password, user.getPasword())) {
            throw new WrongPasswordException();
        }

        return user;
    }
}
