package hu.wilderness.reporterapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String nickName;

    private Date birthDate;

    private String password;

    @NotNull
    private String email;

    private Date createdDate;

    private Date lastLoggedIn;

    private Boolean active;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }
}