package hu.wilderness.reporterapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String nickName;

    private Date birthDate;

    @Column( nullable=false )
    private String password;

    @Column( unique=true, nullable=false )
    private String email;

    private Date createdDate;

    private Date lastLoggedIn;

    private Boolean active;

    @ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<Role> role = new HashSet<Role>();

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

}