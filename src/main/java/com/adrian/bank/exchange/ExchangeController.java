package com.adrian.bank.exchange;

import com.adrian.bank.account.BalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @GetMapping("/exchange/rates")
    public CurrencyRateResponse getRates() {
        return exchangeService.getPostsPlainJSONWithMap ();
    }

    @PostMapping("account/exchange")
    public BalanceResponse exchangeCurrency(BigDecimal amount, long fromAccount, long toAccount) throws Exception {
        return exchangeService.exchangeCurrency (amount, fromAccount, toAccount);
    }





}
