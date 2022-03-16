package com.legallync.userservice.service;


import com.legallync.userservice.entity.User;
import com.legallync.userservice.entity.VerificationToken;
import com.legallync.userservice.model.UserModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    public User updateUser(@RequestBody User userModel, @PathVariable Long id);

    String validateRegisteredToken(String token);

    VerificationToken generateNewToken(String oldToken);

    User findUserByEmail(String email);

    void createPaswordResetTokenForUser(User user, String token);

    String ValidatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    public List<User> getAllClient();

    User findByUserEmailAndPassword(String email, String password);

    void deleteUserById(Long id);
}
