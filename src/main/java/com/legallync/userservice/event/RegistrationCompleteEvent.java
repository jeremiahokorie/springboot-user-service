package com.legallync.userservice.event;

import com.legallync.userservice.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private User user;

    private String applicationurl;
    public RegistrationCompleteEvent(User user, String applicationurl) {
        super(user);
        this.user = user;
       this.applicationurl = applicationurl;
    }
}
