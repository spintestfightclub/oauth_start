package io.levvel.app.passwordReset.emailPasswordResetVerification;

import io.levvel.app.passwordReset.dto.PasswordResetToken;
import io.levvel.app.registration.user.CustomUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserPasswordResetEvent extends ApplicationEvent {
    private final CustomUser user;
    private final PasswordResetToken token;

    public UserPasswordResetEvent(CustomUser user, PasswordResetToken token) {
        super(user);
        this.user = user;
        this.token = token;
    }
}
