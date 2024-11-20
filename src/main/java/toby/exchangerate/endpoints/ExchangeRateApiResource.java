package toby.exchangerate.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toby.exchangerate.ResourceReader;
import toby.exchangerate.ExchangeRateEndpointsApplication;
import toby.exchangerate.json.JsonHandler;
import toby.exchangerate.json.api.exchangerates.LatestCurrencyExchangeRatesResponse;

import java.io.IOException;

@RestController
@RequestMapping(path = "v1")
@RequiredArgsConstructor
public class ExchangeRateApiResource
{
    private static final String USD_LATEST_RESPONSE_PATH = "/mockresponse/exchangerateapi/latest/usdResponse.json";
    private final ResourceReader resourceReader;

    @GetMapping(path = "/latest")
    public LatestCurrencyExchangeRatesResponse getLatestCurrencyExchangeRate() throws IOException
    {
        return JsonHandler.deserializeFromJson(resourceReader.readResource(USD_LATEST_RESPONSE_PATH, ExchangeRateEndpointsApplication.class), LatestCurrencyExchangeRatesResponse.class);
    }
}
