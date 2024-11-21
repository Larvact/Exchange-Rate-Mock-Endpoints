package toby.exchangerate.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import toby.exchangerate.ResourceReader;
import toby.exchangerate.ExchangeRateEndpointsApplication;
import toby.exchangerate.common.exception.api.ApiMalformedRequestException;
import toby.exchangerate.json.JsonHandler;
import toby.exchangerate.json.api.exchangerates.latest.LatestCurrencyExchangeRatesRequest;
import toby.exchangerate.json.api.exchangerates.latest.LatestCurrencyExchangeRatesResponse;

import java.io.IOException;

import static java.lang.StringTemplate.STR;
import static toby.exchangerate.common.data.DataTransformationUtils.orElse;

@RestController
@RequestMapping(path = "v1")
@RequiredArgsConstructor
public class ExchangeRateApiResource
{
    private static final String USD_LATEST_RESPONSE_PATH = "/mockresponse/exchangerateapi/latest/usdResponse.json";
    private final ResourceReader resourceReader;

    @PostMapping(path = "/latest")
    public LatestCurrencyExchangeRatesResponse getLatestCurrencyExchangeRate(@RequestBody final LatestCurrencyExchangeRatesRequest latestCurrencyExchangeRatesRequest) throws IOException
    {
        if(!orElse(latestCurrencyExchangeRatesRequest, request -> orElse(request.getBaseCurrencySymbol(), symbol -> !symbol.isBlank(), false), false))
        {
            throw new ApiMalformedRequestException("Base currency symbol cannot be empty");
        }
        final var baseCurrencySymbol = latestCurrencyExchangeRatesRequest.getBaseCurrencySymbol();
        if("USD".equals(baseCurrencySymbol))
        {
            return JsonHandler.deserializeFromJson(resourceReader.readResource(USD_LATEST_RESPONSE_PATH, ExchangeRateEndpointsApplication.class), LatestCurrencyExchangeRatesResponse.class);
        }
        else
        {
            throw new ApiMalformedRequestException(STR."Base currency symbol [\{baseCurrencySymbol}] is not implemented in the mock data control flow. Implement it or change the request.");
        }
    }
}
