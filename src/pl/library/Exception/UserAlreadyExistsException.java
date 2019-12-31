package pl.library.Exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String mas) {
        super(mas);
    }
}
