package hu.wilderness.reporterapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

    @Setter
    @Getter
    @ToString
    @Entity
    public class Token {


        public Token() {
        }

        public Token(long id) {
            this.id = id;
        }


        public enum TokenType {
            CONFIRMATION,
            FIRSTPASSWORD,
            RESETPASSWORD
        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private boolean active;

        private boolean successful;

        @Column
        @Enumerated(EnumType.STRING)
        private TokenType type;

        @Column(nullable = false)
        private String token;

        @Column(nullable = false)
        private Date createdAt;

        @Column(nullable = false)
        private Date expiresAt;

        private Date confirmedAt;

        @ManyToOne
        private User user;



}
