package hu.wilderness.reporterapp.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportDto {

        private String data;

        private String county;

        private String city;

        private String address;

        private String name;

        private String email;

        private String caseType;
}
