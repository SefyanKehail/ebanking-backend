package app.exceptions;

public class InsufficientFundException extends Exception {
    public InsufficientFundException() {
        super();
    }

    public InsufficientFundException(String message) {
        super(message);
    }
}
