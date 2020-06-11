package kr.co.fastcampus.eatgo.application;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import kr.co.fastcampus.eatgo.exception.DuplicatedEmailException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User registerUser(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicatedEmailException(email);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        User user = User.OfCustomer(name, email, encodedPassword);
        return userRepository.save(user);
    }
}
