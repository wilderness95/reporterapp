package hu.wilderness.reporterapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ReportDto {

        private String lastName;

        private String firstName;

        private String county;

        private String address;

        private String email;

        private String caseType;

        private String notifiedDate;

        private String message;

        private Boolean isDanger;

        private Boolean isAnonym;

}
