package toby.exchangerate.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
