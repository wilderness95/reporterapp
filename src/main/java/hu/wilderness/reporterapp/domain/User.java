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
    private Integer id;

    private String name;

    private String nickName;

    private Date birthDate;

    @Column( nullable=false )
    private String password;

    @Column( nullable=false )
    private String email;

    private Date createdDate;

    private Date lastLoggedIn;

    private Boolean active;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

}