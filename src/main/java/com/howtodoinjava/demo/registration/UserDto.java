package com.howtodoinjava.demo.registration;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/*@NoArgsConstructor
@PasswordConfirmed
@Data*/

public class UserDto {
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    @NotEmpty
    private String username;
    @NotEmpty @Email
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;
}
