package exception;

public class ClientDbValidationException extends RuntimeException {
    public ClientDbValidationException() {
    }

    public ClientDbValidationException(String message) {
        super(message);
    }

    public ClientDbValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
