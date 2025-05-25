// Custom Authentication Exceptions
package in.abhi8290.helloworld.auth.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}