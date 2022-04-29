package hu.wilderness.reporterapp.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporterException extends Exception{

    private String message;

    public ReporterException(String message){
        super();
        this.message = message;
    }


}
