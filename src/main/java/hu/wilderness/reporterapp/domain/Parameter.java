package hu.wilderness.reporterapp.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "value_key")
    private String key;

    private String value;

    private String description;

    @Column(name = "active")
    private Boolean active;
}