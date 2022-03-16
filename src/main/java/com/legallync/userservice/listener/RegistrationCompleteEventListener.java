package com.legallync.userservice.listener;


import com.legallync.userservice.entity.User;
import com.legallync.userservice.event.RegistrationCompleteEvent;
import com.legallync.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        userService.saveVerificationTokenForUser(token, user);

        String url = event.getApplicationurl()
                +"/verifyRegistration?token="
                +token;

        //send verification mail
        log.info("Click the url to verify account:{} ", url);


    }
}
