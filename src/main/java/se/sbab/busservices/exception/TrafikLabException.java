package se.sbab.busservices.exception;

/**
 * TrafikLabException gives Custom Exception For InValid API Keys and TrafikLab API URL
 * @author Parasuram
 */
public class TrafikLabException extends RuntimeException{
    public TrafikLabException(String message) {
        super(message);
    }
}
