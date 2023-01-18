package com.adrian.bank.exchange;

import com.adrian.bank.account.Currency;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
@Log4j2

public class ExchangeService {
    private RestTemplate restTemplate;


    public ExchangeService() {
        this.restTemplate = new RestTemplateBuilder ().build ();
    }

    // TODO: 1/18/2023 mojesh da go mahnesh toya metod veche
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
        // TODO: 1/18/2023 mahni commentara
//        return this.restTemplate.getForObject (url, CurrencyRateResponse.class);
        // TODO: 1/18/2023 nyama nujda ot this reference
        CurrencyRateResponse rates = this.restTemplate.getForObject (url, CurrencyRateResponse.class);
        log.info (rates);
        return rates;
    }


    public BigDecimal getRate(Currency from, Currency to) throws Exception {

        Map<String, BigDecimal> data = getPostsPlainJSONWithMap ().getData ();

        if (from.equals (to)) {
            throw new Exception ("Currencies are the same, go to 'transfer funds' to transfer funds between " +
                    "accounts of same currency");
        } else if (from.equals (Currency.USD)) {
            return data.get (to.name ());
        } else if (to.equals (Currency.USD)) {
            BigDecimal exchangeRate = data.get (from.name ());
            return BigDecimal.ONE.divide (exchangeRate, 4, RoundingMode.HALF_UP);
        } else if (from.equals (Currency.EUR)) {
            // tova ozn, che "to" = BGN, shtoto ako beshe v dolari gorniq sluchai shteshe da go hvane
            BigDecimal usdEURExchangeRate = data.get (Currency.EUR.name ());
            BigDecimal usdBGNExchangeRate = data.get (Currency.BGN.name ());
            return usdBGNExchangeRate.divide (usdEURExchangeRate, 4, RoundingMode.HALF_UP);
        } else if (from.equals (Currency.BGN)) {
            // tova ozn, che "to" = EUR, shtoto ako beshe v dolari vtoriq sluchai shteshe da go hvane
            BigDecimal usdEURExchangeRate = data.get (Currency.EUR.name ());
            BigDecimal usdBGNExchangeRate = data.get (Currency.BGN.name ());
            return usdEURExchangeRate.divide (usdBGNExchangeRate, 4, RoundingMode.HALF_UP);
        }
        throw new Exception ("Accounts relationship not established");
    }


}
