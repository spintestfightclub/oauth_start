package com.howtodoinjava.demo.registration.emailVerification;

import com.howtodoinjava.demo.registration.user.CustomUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegistrationEvent extends ApplicationEvent {
    private final CustomUser user;

    public UserRegistrationEvent(CustomUser user) {
        super(user);
        this.user = user;
    }
}
