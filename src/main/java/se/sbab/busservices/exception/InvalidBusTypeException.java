package se.sbab.busservices.exception;

/**
 * InvalidBusTypeException gives Custom Exception For InValidBusType
 * @author Parasuram
 */
public class InvalidBusTypeException extends RuntimeException{
    public InvalidBusTypeException(String message) {
        super(message);
    }
}
