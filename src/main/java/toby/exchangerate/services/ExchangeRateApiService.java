package toby.exchangerate.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toby.exchangerate.ExchangeRateEndpointsApplication;
import toby.exchangerate.ResourceReader;
import toby.exchangerate.common.exception.api.ApiMalformedRequestException;
import toby.exchangerate.json.JsonHandler;
import toby.exchangerate.json.api.exchangerates.latest.LatestCurrencyExchangeRatesResponse;

import java.io.IOException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExchangeRateApiService
{
    private static final String USD_LATEST_RESPONSE_PATH = "/mockresponse/exchangerateapi/latest/usdResponse.json";

    private final ResourceReader resourceReader;

    public LatestCurrencyExchangeRatesResponse getLatestCurrencyExchangeRatesResponse(final String baseCurrencySymbol, final Set<String> responseCurrencySymbols) throws IOException
    {
        if(baseCurrencySymbol.isBlank())
        {
            throw new ApiMalformedRequestException("Base currency symbol cannot be empty");
        }
        if("USD".equals(baseCurrencySymbol))
        {
            return JsonHandler.deserializeFromJson(resourceReader.readResource(USD_LATEST_RESPONSE_PATH, ExchangeRateEndpointsApplication.class), LatestCurrencyExchangeRatesResponse.class);
        }
        else
        {
            throw new ApiMalformedRequestException(String.format("Base currency symbol [%s] is not implemented in the mock data control flow. Implement it or change the request.", baseCurrencySymbol));
        }
    }
}
