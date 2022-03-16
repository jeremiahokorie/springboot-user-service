package com.legallync.userservice.service;

import com.legallync.userservice.Exception.CustomException;
import com.legallync.userservice.entity.PasswordResetToken;
import com.legallync.userservice.entity.User;
import com.legallync.userservice.entity.VerificationToken;
import com.legallync.userservice.model.UserModel;
import com.legallync.userservice.repository.PasswordResetTokenRepository;
import com.legallync.userservice.repository.UserRepository;
import com.legallync.userservice.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public User registerUser(UserModel userModel) {
//        userRepository.findByUserName(userModel.getUserName()).isPresent()
//            new CustomException("User already exist", HttpStatus.CONFLICT);


        User user = new User();
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setEmail(userModel.getEmail());
        user.setRole("USER");
        user.setUserName(userModel.getUserName());
        user.setPhone(userModel.getPhone());
        user.setCreated(new Date());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userRepository.save(user);

        return user;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    public User updateUser(User userModel, Long id) {
        return userRepository.findById(id)
                .map(att -> {
                    att.setFirstName(userModel.getFirstName());
                    att.setLastName(userModel.getLastName());
                    return userRepository.save(userModel);
                })
                .orElseGet(() -> {
                    userModel.setUserid(id);
                    return userRepository.save(userModel);
                });
    }

    @Override
    public String validateRegisteredToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken ==null){
            throw new CustomException("Invalid Token",HttpStatus.BAD_REQUEST);
        }

        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if (verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()<=0){
            verificationTokenRepository.delete(verificationToken);

            return "expired";

        }

        user.setEnabled(true);
        userRepository.save(user);

        return "valid";
    }

    @Override
    public VerificationToken generateNewToken(String oldToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public User findUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public void createPaswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user,token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String ValidatePasswordResetToken(String token) {

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken ==null){
            throw new CustomException("Invalid Token",HttpStatus.BAD_REQUEST);
        }

        User user = passwordResetToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if (passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime()<=0){
            passwordResetTokenRepository.delete(passwordResetToken);

            return "expired";

        }

        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }



    public List<User> getAllClient(){
        return userRepository.findAll();
    }

    @Override
    public User findByUserEmailAndPassword(String email, String password) {
       // User loginUser = userRepository.findByEmail(email);

        User loginUser = userRepository.findByEmailAndPassword(email,password);
        if (loginUser == null){
             throw new CustomException("Invalid User details");
        }else {
            return userRepository.findByEmailAndPassword(email,password);
        }
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
