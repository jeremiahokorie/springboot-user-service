package com.legallync.userservice.controller;

import com.legallync.userservice.Exception.CustomException;
import com.legallync.userservice.entity.User;
import com.legallync.userservice.entity.VerificationToken;
import com.legallync.userservice.event.RegistrationCompleteEvent;
import com.legallync.userservice.model.PasswordModel;
import com.legallync.userservice.model.UserModel;
import com.legallync.userservice.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/client-service")
@Api(value = "Legallync API Documentation")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/register")
    public String RegisterUser(@RequestBody UserModel userModel, final HttpServletRequest request){
        User user = userService.registerUser(userModel);
        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(
                user,
               applicationUrl(request)
        ));
        return "Successfully Registered";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistrationToken(@RequestParam("token") String token){
        String result = userService.validateRegisteredToken(token);

        if (result.equalsIgnoreCase("valid")){
            return "User verified successfully";
        }
        return "Bad request";

    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel,HttpServletRequest request) {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        String url = "";
        if (user != null) {
            String token = UUID.randomUUID().toString();
            userService.createPaswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(user, applicationUrl(request), token);
        }

        return url;
    }

    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url = applicationUrl
                +"/savePassword?token="
                +token;

        //send verification mail
        log.info("Click the url to reset passowrd:{} ", url);

        return url;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel){
        String result = userService.ValidatePasswordResetToken(token);
        if (!result.contentEquals("valid")){
           throw new   CustomException("Invalid Token", HttpStatus.BAD_REQUEST);
        }

        Optional<User> user = userService.getUserByPasswordResetToken(token);

            if (user.isPresent()){
                userService.changePassword(user.get(),passwordModel.getNewPassword());
                return "Password Reset Successfully";
            }else {
                return "Bad Token";
            }
    }

    @GetMapping("/resendVerificationToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken,HttpServletRequest request){
        VerificationToken verificationToken = userService.generateNewToken(oldToken);

        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request),verificationToken);
        return "Verification mail sent";
    }

    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl
                +"/verifyRegistration?token="
                +verificationToken.getToken();
        //send verification mail
        log.info("Click the url to verify account:{} ", url);
    }
    private String applicationUrl(HttpServletRequest request){
        return "http://"+request.getServerName()
                +":"+ request.getServerPort() + request.getContextPath();
    }

    @PutMapping("/user/{id}")
    public User updateClient(@RequestBody User user, @PathVariable Long id){
        return userService.updateUser(user,id);

    }

    @GetMapping("/users")
    public List<User> getAllClient(){
        return userService.getAllClient();
    }

    @PostMapping("/login")
    public User login(String email, String password){
        User user1 = userService.findByUserEmailAndPassword(email,password);
        return user1;
    }

    @DeleteMapping("/client/{id}")
    public void deleteClientById(@PathVariable("id") Long id){
        userService.deleteUserById(id);
    }






}
