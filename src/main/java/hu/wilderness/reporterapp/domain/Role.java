package hu.wilderness.reporterapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
@Entity
public class Role {

    @Id
    @GeneratedValue
    private int id;

    private String role;

    @ManyToMany(mappedBy = "role")
    private Set<User> users = new HashSet<User>();

}