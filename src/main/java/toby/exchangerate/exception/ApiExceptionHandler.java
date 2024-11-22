package toby.exchangerate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import toby.exchangerate.common.exception.api.ApiMalformedRequestException;
import toby.exchangerate.common.exception.api.ApiResponseErrorDetail;

import java.io.IOException;
import java.time.Instant;

@ControllerAdvice
public class ApiExceptionHandler
{
    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<ApiResponseErrorDetail> handleInternalServerErrorExceptions(final Exception exception)
    {
        final var internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        final var responseException = new ApiResponseErrorDetail(exception.getMessage(), null, internalServerError, Instant.now());
        return new ResponseEntity<>(responseException, internalServerError);
    }

    @ExceptionHandler(value = ApiMalformedRequestException.class)
    public ResponseEntity<ApiResponseErrorDetail> handleBadRequestException(final Exception badRequestException)
    {
        final var internalServerError = HttpStatus.BAD_REQUEST;
        final var responseException = new ApiResponseErrorDetail(badRequestException.getMessage(), null, internalServerError, Instant.now());
        return new ResponseEntity<>(responseException, internalServerError);
    }
}
