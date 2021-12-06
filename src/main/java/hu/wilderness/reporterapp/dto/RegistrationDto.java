package hu.wilderness.reporterapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegistrationDto {

    @NotNull
    private String name;

    @NotNull
    private String nickName;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @Override
    public String toString() {
        return "RegistrationDto{" +
                "name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}