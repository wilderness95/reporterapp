package hu.wilderness.reporterapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
@Setter
@Getter
@ToString
@Entity
public class EmailTemplate {


    public enum MailTemplateType {
        REGISTRATION
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private boolean active;

    @Column
    @Enumerated(EnumType.STRING)
    private MailTemplateType type;

    @Column
    private String subject;

    @Column(columnDefinition="TEXT")
    private String data;

}
