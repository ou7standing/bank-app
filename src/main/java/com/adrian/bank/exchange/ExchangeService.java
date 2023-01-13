package com.adrian.bank.exchange;

import com.adrian.bank.account.Account;
import com.adrian.bank.account.AccountService;
import com.adrian.bank.account.BalanceResponse;
import com.adrian.bank.account.Currency;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Log4j2

public class ExchangeService {
    private RestTemplate restTemplate;
    @Autowired
    public AccountService accountService;


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

    public BalanceResponse exchangeCurrency(double amount, long fromAccount, long toAccount) throws Exception {

        Account giveTo = accountService.findAccount (toAccount);

        if (accountService.findAccount (fromAccount).getCurrency ().equals (Currency.USD)) {

            accountService.withdrawal (fromAccount, amount);

            double rate = getPostsPlainJSONWithMap ().getData ().get (giveTo.getCurrency ().name ());

            log.info (String.valueOf (rate));

            double depositAmount = amount * rate;

            BigDecimal depositAmountToBD = new BigDecimal (depositAmount).setScale (2, RoundingMode.HALF_UP);

            depositAmount = depositAmountToBD.doubleValue ();

            log.info (String.valueOf (depositAmount));

            accountService.deposit (toAccount, depositAmount);

            return new BalanceResponse (giveTo.getBalance (), giveTo.getCurrency ());

        } else if ((!accountService.findAccount (fromAccount).getCurrency ().equals (Currency.USD)) &&
                (giveTo.getCurrency ().equals (Currency.USD))) {

            accountService.withdrawal (fromAccount, amount);

            Account takeFrom = accountService.findAccount (fromAccount);

            double exchangeRate = getPostsPlainJSONWithMap ().getData ().get (takeFrom.getCurrency ().name ());

            log.info (String.valueOf (exchangeRate));

            double depositAmount = amount / exchangeRate;

            BigDecimal depositAmountToBD = new BigDecimal (depositAmount).setScale (2, RoundingMode.HALF_UP);

            depositAmount = depositAmountToBD.doubleValue ();

            log.info (String.valueOf (depositAmount));

            accountService.deposit (toAccount, depositAmount);

            return new BalanceResponse (giveTo.getBalance (), giveTo.getCurrency ());


        } else if (accountService.findAccount (fromAccount).getCurrency ().equals (Currency.EUR)) {
            // tova oznachava, che giveTo accounta e v BGN, shtoto ako beshe v dolari gorniq sluchai
            // shteshe da go hvane

            accountService.withdrawal (fromAccount, amount);

//            Account takeFrom = accountService.findAccount (fromAccount);

            double usdEURExchangeRate = getPostsPlainJSONWithMap ().getData ().get (Currency.EUR.name ());

            log.info (String.valueOf (usdEURExchangeRate));

            double depositInUSD = amount / usdEURExchangeRate;

            double usdBGNExchangeRate = getPostsPlainJSONWithMap ().getData ().get (Currency.BGN.name ());

            double depositAmount = depositInUSD * usdBGNExchangeRate;

            BigDecimal depositAmountToBD = new BigDecimal (depositAmount).setScale (2, RoundingMode.HALF_UP);

            depositAmount = depositAmountToBD.doubleValue ();

            log.info (String.valueOf (depositAmount));

            accountService.deposit (toAccount, depositAmount);

            return new BalanceResponse (giveTo.getBalance (), giveTo.getCurrency ());

        } else if (accountService.findAccount (fromAccount).getCurrency ().equals (Currency.BGN)) {
            // tova oznachava, che giveTo accounta e v EUR, shtoto ako beshe v dolari vtoriq sluchai
            // shteshe da go hvane

            accountService.withdrawal (fromAccount, amount);

//            Account takeFrom = accountService.findAccount (fromAccount);

            double usdBGNExchangeRate = getPostsPlainJSONWithMap ().getData ().get (Currency.BGN.name ());

            log.info (String.valueOf (usdBGNExchangeRate));

            double depositInUSD = amount / usdBGNExchangeRate;

            double usdEURExchangeRate = getPostsPlainJSONWithMap ().getData ().get (Currency.EUR.name ());

            double depositAmount = depositInUSD * usdEURExchangeRate;

            BigDecimal depositAmountToBD = new BigDecimal (depositAmount).setScale (2, RoundingMode.HALF_UP);

            depositAmount = depositAmountToBD.doubleValue ();

            log.info (String.valueOf (depositAmount));

            accountService.deposit (toAccount, depositAmount);

            return new BalanceResponse (giveTo.getBalance (), giveTo.getCurrency ());

        }
        throw new Exception ("Accounts relationship not established");


    }


}
