package in.abhi8290.helloworld.user.exception;

public class InvalidEmailProvidedException extends RuntimeException {
  public InvalidEmailProvidedException(String message) {
    super(message);
  }
}
