package toby.exchangerate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class TestResourceReader
{
    private static final String EXCHANGE_RATE_API_LATEST_USD_PATH = "/mockresponse/exchangerateapi/latest/usdResponse.json";
    @Autowired
    private ResourceReader resourceReader;

    @Test
    void validResourceAndClassPath_readResource_correctStringReturned() throws IOException
    {
        assertFalse(resourceReader.readResource(EXCHANGE_RATE_API_LATEST_USD_PATH, ExchangeRateEndpointsApplication.class).isBlank());
    }
}