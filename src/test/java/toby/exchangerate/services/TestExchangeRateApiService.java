package toby.exchangerate.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import toby.exchangerate.ResourceReader;
import toby.exchangerate.common.exception.api.ApiMalformedRequestException;

import java.util.Collections;

@SpringBootTest
class TestExchangeRateApiService
{
    @MockBean
    private ResourceReader resourceReader;
    @Autowired
    private ExchangeRateApiService exchangeRateApiService;

    @ParameterizedTest
    @NullAndEmptySource
    void blankBaseCurrencySymbol_getLatestCurrencyExchangeRatesResponse_malformedExceptionThrown(final String baseCurrencySymbol)
    {
        final var malformedException = Assertions.assertThrows(ApiMalformedRequestException.class, () -> exchangeRateApiService.getLatestCurrencyExchangeRatesResponse(baseCurrencySymbol, Collections.emptySet()));

        Assertions.assertEquals("Base currency symbol cannot be empty", malformedException.getMessage());
    }
}