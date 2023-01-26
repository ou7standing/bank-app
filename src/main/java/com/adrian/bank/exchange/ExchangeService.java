package com.adrian.bank.exchange;

import com.adrian.bank.account.Currency;
import com.adrian.bank.exception.SpringBootExc;
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
    private final RestTemplate restTemplate;

    public ExchangeService() {
        this.restTemplate = new RestTemplateBuilder ().build ();
    }

    public CurrencyRateResponse getPostsPlainJSONWithMap() {
        String url = "https://api.freecurrencyapi.com/v1/latest?apikey=qqAnm7mqws7DWUvbeNAY4LXddS0XibxVKhWBM3fB";
        CurrencyRateResponse rates = restTemplate.getForObject (url, CurrencyRateResponse.class);
        log.info (rates);
        return rates;
    }

    public BigDecimal getRate(Currency from, Currency to) {
        Map<String, BigDecimal> data = getPostsPlainJSONWithMap ().getData ();

        if (from.equals (to))
            throw new SpringBootExc ("Cannot exchange identical currencies, use /transactions/transfer");

        if (isNull (data.get (from.name ())) || isNull (data.get (to.name ())))
            throw new SpringBootExc ("Accounts relationship not established");

        if (from.equals (Currency.USD))
            return data.get (to.name ());

        if (to.equals (Currency.USD))
            return fromCurrencyToUSD (data.get (from.name ()));

        var fromWhateverToUSD = fromCurrencyToUSD (data.get (from.name ()));
        var fromUSDToWhatever = data.get (to.name ());
        return fromWhateverToUSD.multiply (fromUSDToWhatever);
    }

    private static BigDecimal fromCurrencyToUSD(BigDecimal usdToCurrency) {
        return ONE.divide (usdToCurrency, 4, HALF_UP);
    }
}