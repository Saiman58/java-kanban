package exceptions;

public class ManagerSaveException extends RuntimeException {

    public ManagerSaveException(String message, Exception cause) {
        super(message, cause);
    }

    public ManagerSaveException(String message) {
        super(message);
    }

}


