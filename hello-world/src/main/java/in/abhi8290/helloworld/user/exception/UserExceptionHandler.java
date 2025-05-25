package in.abhi8290.helloworld.user.exception;

import in.abhi8290.helloworld.core.exception.ErrorResponseDto;
import in.abhi8290.helloworld.core.exception.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice(assignableTypes = in.abhi8290.helloworld.user.UserController.class)
public class UserExceptionHandler extends GlobalExceptionHandler {
    public final Logger logger = LoggerFactory.getLogger(UserExceptionHandler.class);

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistsException(UserAlreadyExistsException ex,  WebRequest request) {
        logger.warn("User already exists: {}", ex.getMessage());

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getMessage(),
                "USER_ALREADY_EXISTS",
                HttpStatus.CONFLICT.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
