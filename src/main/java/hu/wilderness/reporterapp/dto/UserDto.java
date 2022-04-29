package hu.wilderness.reporterapp.dto;

import hu.wilderness.reporterapp.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
public class UserDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String county;

    @NotNull
    private String role;
}
