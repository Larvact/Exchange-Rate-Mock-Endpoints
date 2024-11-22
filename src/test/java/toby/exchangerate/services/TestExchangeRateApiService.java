package toby.exchangerate.services;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import toby.exchangerate.ExchangeRateEndpointsApplication;
import toby.exchangerate.ResourceReader;
import toby.exchangerate.common.exception.api.ApiMalformedRequestException;
import toby.exchangerate.json.api.exchangerates.latest.LatestCurrencyExchangeRatesResponse;

import java.io.IOException;
import java.util.Collections;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class TestExchangeRateApiService
{
    private static final String MOCK_USD_LATEST_EXCHANGE_RATES_RESPONSE = """
            {
              "success": true,
              "timestamp": 1519296206,
              "base": "USD",
              "rates": {
                "GBP": 0.72007,
                "JPY": 107.346001,
                "EUR": 0.813399
              }
            }""";

    @MockBean
    private ResourceReader resourceReader;
    @Autowired
    private ExchangeRateApiService exchangeRateApiService;

    @ParameterizedTest
    @NullAndEmptySource
    void blankBaseCurrencySymbol_getLatestCurrencyExchangeRatesResponse_malformedExceptionThrown(final String baseCurrencySymbol)
    {
        final var malformedException = assertThrows(ApiMalformedRequestException.class, () -> exchangeRateApiService.getLatestCurrencyExchangeRatesResponse(baseCurrencySymbol, Collections.emptySet()));

        assertEquals("Base currency symbol cannot be empty", malformedException.getMessage());
    }

    @Test
    void usdCurrencySymbol_getLatestCurrencyExchangeRatesResponse_responseReturned() throws IOException
    {
        when(resourceReader.readResource("/mockresponse/exchangerateapi/latest/usdResponse.json", ExchangeRateEndpointsApplication.class)).thenReturn(MOCK_USD_LATEST_EXCHANGE_RATES_RESPONSE);

        final var latestCurrencyExchangeRatesResponse = exchangeRateApiService.getLatestCurrencyExchangeRatesResponse("USD", Collections.emptySet());

        assertThat(latestCurrencyExchangeRatesResponse)
                .has(new Condition<>(response -> response.getCurrencyExchangeRates().size() == 3, "The response has 3 exchange rate values"))
                .has(new Condition<>(response -> nonNull(response.getTimestamp()), "Timestamp has been set"))
                .extracting(LatestCurrencyExchangeRatesResponse::getIsSuccess, LatestCurrencyExchangeRatesResponse::getBaseCurrency)
                .containsExactly(true, "USD");
    }

    @Test
    void invalidCurrencySymbol_getLatestCurrencyExchangeRatesResponse_malformedExceptionThrown()
    {
        final var invalidSymbol = "invalid-symbol";

        final var malformedException = assertThrows(ApiMalformedRequestException.class, () -> exchangeRateApiService.getLatestCurrencyExchangeRatesResponse(invalidSymbol, Collections.emptySet()));

        assertEquals(format("Base currency symbol [%s] is not implemented in the mock data control flow. Implement it or change the request.", invalidSymbol), malformedException.getMessage());
    }
}