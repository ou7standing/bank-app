package com.adrian.bank.exchange;

import com.adrian.bank.account.Currency;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_UP;
import static java.util.Objects.isNull;

@Service
@Log4j2

public class ExchangeService {
    private RestTemplate restTemplate;


    public ExchangeService() {
        this.restTemplate = new RestTemplateBuilder ().build ();
    }

    public String getPostsPlainJSON() {
        String url = "https://api.freecurrencyapi.com/v1/latest?apikey=qqAnm7mqws7DWUvbeNAY4LXddS0XibxVKhWBM3fB";
//        return this.restTemplate.getForObject (url, String.class);
        return " {\n" +
                "  \"data\": {\n" +
                "    \"AUD\": 1.486352,\n" +
                "    \"BGN\": 1.84258,\n" +
                "    \"BRL\": 5.293808,\n" +
                "    \"CAD\": 1.352752,\n" +
                "    \"CHF\": 0.929401,\n" +
                "    \"CNY\": 6.960113,\n" +
                "    \"CZK\": 22.801628,\n" +
                "    \"DKK\": 6.991014,\n" +
                "    \"EUR\": 0.940131,\n" +
                "    \"GBP\": 0.831521,\n" +
                "    \"HKD\": 7.800663,\n" +
                "    \"HRK\": 7.096712,\n" +
                "    \"HUF\": 377.020511,\n" +
                "    \"IDR\": 15620.015654,\n" +
                "    \"ILS\": 3.519135,\n" +
                "    \"INR\": 82.776474,\n" +
                "    \"ISK\": 142.990245,\n" +
                "    \"JPY\": 133.489256,\n" +
                "    \"KRW\": 1272.606855,\n" +
                "    \"MXN\": 19.477138,\n" +
                "    \"MYR\": 4.423006,\n" +
                "    \"NOK\": 9.829948,\n" +
                "    \"NZD\": 1.594253,\n" +
                "    \"PHP\": 55.825087,\n" +
                "    \"PLN\": 4.40037,\n" +
                "    \"RON\": 4.633007,\n" +
                "    \"RUB\": 70.000078,\n" +
                "    \"SEK\": 10.478293,\n" +
                "    \"SGD\": 1.348102,\n" +
                "    \"THB\": 34.630051,\n" +
                "    \"TRY\": 18.662019,\n" +
                "    \"USD\": 1,\n" +
                "    \"ZAR\": 17.208534\n" +
                "  }\n" +
                "} ";
    }

    public CurrencyRateResponse getPostsPlainJSONWithMap() {
        String url = "https://api.freecurrencyapi.com/v1/latest?apikey=qqAnm7mqws7DWUvbeNAY4LXddS0XibxVKhWBM3fB";
//        return this.restTemplate.getForObject (url, CurrencyRateResponse.class);
        CurrencyRateResponse rates = this.restTemplate.getForObject (url, CurrencyRateResponse.class);
        log.info (rates);
        return rates;
    }


    public BigDecimal getRate(Currency from, Currency to) throws Exception {
        Map<String, BigDecimal> data = getPostsPlainJSONWithMap().getData();

        if (from.equals(to))
            throw new Exception("Cannot exchange identical currencies, use /transactions/transfer");

        if (isNull(data.get(from)) || isNull(data.get(to)))
            throw new Exception("Accounts relationship not established");

        if (from.equals(Currency.USD))
            return data.get(to.name());

        if (to.equals(Currency.USD))
            return fromCurrencyToUSD(data.get(from.name()));

        var fromWhateverToUSD = fromCurrencyToUSD(data.get(from.name()));
        var fromUSDToWhatever = data.get(to.name());
        return fromWhateverToUSD.multiply(fromUSDToWhatever);
    }

    private static BigDecimal fromCurrencyToUSD(BigDecimal usdToCurrency) {
        return ONE.divide(usdToCurrency, 4, HALF_UP);
    }
}
