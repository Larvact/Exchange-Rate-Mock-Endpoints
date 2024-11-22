package toby.exchangerate.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import toby.exchangerate.ResourceReader;
import toby.exchangerate.ExchangeRateEndpointsApplication;
import toby.exchangerate.common.exception.api.ApiMalformedRequestException;
import toby.exchangerate.json.JsonHandler;
import toby.exchangerate.json.api.exchangerates.latest.LatestCurrencyExchangeRatesResponse;
import toby.exchangerate.services.ExchangeRateApiService;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping(path = "v1")
@RequiredArgsConstructor
public class ExchangeRateApiResource
{
    private final ExchangeRateApiService exchangeRateApiService;

    @GetMapping(path = "/latest")
    public LatestCurrencyExchangeRatesResponse getLatestCurrencyExchangeRate(@RequestParam(name = "base") String baseCurrencySymbol, @RequestParam(name = "symbols") Set<String> responseCurrencySymbols) throws IOException
    {
        return exchangeRateApiService.getLatestCurrencyExchangeRatesResponse(baseCurrencySymbol, responseCurrencySymbols);
    }
}
