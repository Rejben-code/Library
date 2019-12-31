package pl.library.Exception;

public class NoSuchFileException extends RuntimeException {
  public NoSuchFileException (String msg){
      super(msg);
  }
}
