package se.sbab.busservices.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
/**
 * ExceptionResponse Gives ExceptionResponse
 * @author Parasuram
 */
public class ExceptionResponse {

    private String message;
    private LocalDateTime dateTime;

}