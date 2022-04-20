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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean active;

    private String lastName;

    private String firstName;

    private String county;

    private String address;

    private String email;

    private String caseType;

    private Date notifiedDate;

    private String message;

    private Boolean isDanger;

    private String img;

    private Date createdDate;

    private Boolean isAnonym;

    private String ipAddress;

    @OneToOne
    @JoinColumn(name = "token_id", nullable = true)
    private Token token;

}

