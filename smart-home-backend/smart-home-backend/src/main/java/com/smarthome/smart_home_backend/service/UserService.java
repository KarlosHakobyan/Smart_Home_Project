package com.smarthome.smart_home_backend.service;

import com.smarthome.smart_home_backend.model.User;
import com.smarthome.smart_home_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );
    }

    public User createUser(User user) {

        user.setCreatedAt(
                LocalDateTime.now()
        );

        return userRepository.save(user);
    }

    public User updateUser(
            Long id,
            User updated
    ) {

        User user = getUserById(id);

        user.setName(updated.getName());
        user.setEmail(updated.getEmail());
        user.setPasswordHash(updated.getPasswordHash());
        user.setRole(updated.getRole());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}