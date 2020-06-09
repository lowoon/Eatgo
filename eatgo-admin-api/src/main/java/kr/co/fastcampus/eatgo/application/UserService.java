package kr.co.fastcampus.eatgo.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(String name, String email) {
        User user = User.builder()
            .name(name)
            .email(email)
            .build();

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, String name, String email, Long level) {
        User user = User.builder()
            .id(id)
            .name(name)
            .email(email)
            .level(level)
            .build();

        user.update(id, name, email, level);
        return user;
    }

    @Transactional
    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
        user.deactivate();
    }
}
