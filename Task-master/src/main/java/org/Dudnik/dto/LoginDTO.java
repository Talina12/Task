package org.Dudnik.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.Dudnik.security.SafeInput;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginDTO {
    @NonNull
    @SafeInput
    @Size(min = 8, max = 15)
    private String password;

    @NonNull
    @Pattern(regexp = "\\S+@\\S+") // needs to define a correct expression
    private String email;
}
