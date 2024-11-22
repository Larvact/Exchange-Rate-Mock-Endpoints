package toby.exchangerate.exception;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import toby.exchangerate.common.exception.api.ApiMalformedRequestException;
import toby.exchangerate.common.exception.api.ApiResponseErrorDetail;

import java.io.IOException;

import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;

class TestApiExceptionHandler
{
    private static final String EXCEPTION_MESSAGE = "Unit test exception";
    private ApiExceptionHandler apiExceptionHandler;

    @BeforeEach
    void setup()
    {
        apiExceptionHandler = new ApiExceptionHandler();
    }

    @Test
    void ioException_handleInternalServerErrorExceptions_correctResponseEntityReturned()
    {
        final var ioException = new IOException(EXCEPTION_MESSAGE);

        final ResponseEntity<ApiResponseErrorDetail> responseEntity = apiExceptionHandler.handleInternalServerErrorExceptions(ioException);

        assertThat(responseEntity)
                .has(new Condition<>(entity -> HttpStatus.INTERNAL_SERVER_ERROR == entity.getStatusCode(), "The response code is internal error"))
                .extracting(HttpEntity::getBody)
                .has(new Condition<>(errorDetail -> nonNull(errorDetail.getTimestamp()), "Timestamp has been set to the error response"))
                .extracting(ApiResponseErrorDetail::getMessage, ApiResponseErrorDetail::getCause, ApiResponseErrorDetail::getHttpStatus)
                .containsExactly(EXCEPTION_MESSAGE, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void apiMalformedRequestException_handleInternalServerErrorExceptions_correctResponseEntityReturned()
    {
        final var apiMalformedRequestException = new ApiMalformedRequestException(EXCEPTION_MESSAGE);

        final ResponseEntity<ApiResponseErrorDetail> responseEntity = apiExceptionHandler.handleBadRequestException(apiMalformedRequestException);

        assertThat(responseEntity)
                .has(new Condition<>(entity -> HttpStatus.BAD_REQUEST == entity.getStatusCode(), "The response code is bad request"))
                .extracting(HttpEntity::getBody)
                .has(new Condition<>(errorDetail -> nonNull(errorDetail.getTimestamp()), "Timestamp has been set to the error response"))
                .extracting(ApiResponseErrorDetail::getMessage, ApiResponseErrorDetail::getCause, ApiResponseErrorDetail::getHttpStatus)
                .containsExactly(EXCEPTION_MESSAGE, null, HttpStatus.BAD_REQUEST);
    }
}