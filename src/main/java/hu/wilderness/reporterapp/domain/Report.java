package hu.wilderness.reporterapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@ToString
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private boolean active;

    @Column(columnDefinition="TEXT")
    private String data;

    private String county;

    private String city;

    private String address;

    private String name;

    private String email;

    private String caseType;

    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
