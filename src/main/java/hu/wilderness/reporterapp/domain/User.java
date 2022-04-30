package hu.wilderness.reporterapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;


@Entity
@Getter
@Setter
@ToString
public class User {


    public enum UserRole {
        ROLE_USER,
        ROLE_ADMIN
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String lastName;

    private String firstName;

    private String email;

    private String phoneNumber;

    private String password;

    private String county;

    private Date createdDate;

    private Date lastLoggedIn;

    private Boolean active;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole roleName;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

}