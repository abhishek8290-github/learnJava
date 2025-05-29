package in.abhi8290.helloworld.core.exception;

import in.abhi8290.helloworld.auth.exception.IncorrectPasswordException;
import in.abhi8290.helloworld.auth.exception.UserNotFoundException;
import in.abhi8290.helloworld.core.exception.common.InvalidTokenError;
import in.abhi8290.helloworld.user.exception.InvalidEmailProvidedException;
import in.abhi8290.helloworld.user.exception.UserAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleGenericException(
      Exception ex, WebRequest request) {

    logger.error("Unexpected error occurred: ", ex);

    logger.error("Caught in global: {}", ex.getClass().getSimpleName(), ex);

    ErrorResponseDto errorResponse = new ErrorResponseDto(
        "An unexpected error occurred Bruh ",
        "INTERNAL_SERVER_ERROR",
        HttpStatus.INTERNAL_SERVER_ERROR.value());

    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(
      UserNotFoundException ex, WebRequest request) {

    logger.warn("User not found: {}", ex.getMessage());

    ErrorResponseDto errorResponse = new ErrorResponseDto(
        ex.getMessage(),
        "USER_NOT_FOUND",
        HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(IncorrectPasswordException.class)
  public ResponseEntity<ErrorResponseDto> handleIncorrectPasswordException(
      IncorrectPasswordException ex, WebRequest request) {

    logger.warn("Authentication failed: {}", ex.getMessage());

    ErrorResponseDto errorResponse = new ErrorResponseDto(
        ex.getMessage(),
        "INVALID_CREDENTIALS",
        HttpStatus.UNAUTHORIZED.value());

    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistsException(
      UserAlreadyExistsException ex, WebRequest request) {
    logger.warn("User already exists: {}", ex.getMessage());

    ErrorResponseDto errorResponse = new ErrorResponseDto(
        ex.getMessage(),
        "USER_ALREADY_EXISTS",
        HttpStatus.CONFLICT.value());

    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(InvalidTokenError.class)
  public ResponseEntity<ErrorResponseDto> handleInvalidTokenException(InvalidTokenError ex, WebRequest request) {
    logger.warn("Invalid token: {}", ex.getMessage());

    ErrorResponseDto errorResponse = new ErrorResponseDto(
        ex.getMessage(),
        "INVALID_TOKEN",
        HttpStatus.UNAUTHORIZED.value());

    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

  }

  @ExceptionHandler(InvalidEmailProvidedException.class)
  public ResponseEntity<ErrorResponseDto> handleInValidEmailErrorException(InvalidEmailProvidedException ex,
      WebRequest request) {
    logger.warn("Invalid Email Provided : {}", ex.getMessage());

    ErrorResponseDto errorResponse = new ErrorResponseDto(
        ex.getMessage(),
        "INVALID_EMAIL",
        HttpStatus.UNAUTHORIZED.value());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

}
