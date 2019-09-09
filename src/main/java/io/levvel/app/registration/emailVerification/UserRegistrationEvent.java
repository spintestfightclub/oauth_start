package io.levvel.app.registration.emailVerification;

import io.levvel.app.registration.user.CustomUser;
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
