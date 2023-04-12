package se.sbab.busservices.exception;

/**
 * InvalidBusTypeException gives Custom Exception For InValidBusType Input
 * @author Parasuram
 */
public class InValidBusTypeException extends RuntimeException{
    public InValidBusTypeException(String message) {
        super(message);
    }
}
