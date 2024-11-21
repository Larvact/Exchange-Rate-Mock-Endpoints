package toby.exchangerate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import toby.exchangerate.common.exception.ApiResponseException;

import java.io.IOException;
import java.time.Instant;

@ControllerAdvice
public class ApiExceptionHandler
{
    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<Object> handleInternalServerErrorExceptions(final Exception exception)
    {
        final var internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        final var responseException = new ApiResponseException(exception.getMessage(), exception, internalServerError, Instant.now());
        return new ResponseEntity<>(responseException, internalServerError);
    }
}
