package in.abhi8290.helloworld.auth.exception;

import in.abhi8290.helloworld.core.exception.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import in.abhi8290.helloworld.core.exception.GlobalExceptionHandler;

@RestControllerAdvice(basePackages = "in.abhi8290.helloworld.auth")
public class AuthExceptionHandler extends GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(
            UserNotFoundException ex, WebRequest request) {

        logger.warn("User not found: {}", ex.getMessage());

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getMessage(),
                "USER_NOT_FOUND",
                HttpStatus.NOT_FOUND.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorResponseDto> handleIncorrectPasswordException(
            IncorrectPasswordException ex, WebRequest request) {

        logger.warn("Authentication failed: {}", ex.getMessage());

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getMessage(),
                "INVALID_CREDENTIALS",
                HttpStatus.UNAUTHORIZED.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }


}