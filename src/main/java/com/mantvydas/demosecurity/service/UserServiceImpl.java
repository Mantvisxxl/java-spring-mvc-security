package com.mantvydas.demosecurity.service;

import com.mantvydas.demosecurity.dao.UserRepository;
import com.mantvydas.demosecurity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Override
    public void updateUserPassword(String username, String newPassword) {
        User user = getUserByUsername(username);
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public boolean isPasswordCorrect(String username, String currentPassword) {

        Optional<User> userToCheck = userRepository.findByUsername(username);

        return userToCheck.map(user -> passwordEncoder.matches(currentPassword, user.getPassword()))
                .orElse(false);
    }
}
