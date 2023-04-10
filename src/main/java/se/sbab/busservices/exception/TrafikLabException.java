package se.sbab.busservices.exception;

/**
 * TrafikLabException gives Custom Exception For Parsing BusLine Errors
 * @author Parasuram
 */
public class TrafikLabException extends RuntimeException{
    public TrafikLabException(String message) {
        super(message);
    }
}
