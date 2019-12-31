package pl.library.Exception;

public class PublicationAlreadyExistsException extends RuntimeException {
    public PublicationAlreadyExistsException(String ma) {
        super(ma);
    }
}
