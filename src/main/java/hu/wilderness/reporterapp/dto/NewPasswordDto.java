package hu.wilderness.reporterapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewPasswordDto {

    private String uuid;

    private String password1;

    private String password2;

    public NewPasswordDto(String uuId) {
    }
    public NewPasswordDto() {
    }
}
