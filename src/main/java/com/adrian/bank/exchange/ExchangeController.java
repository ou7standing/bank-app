package com.adrian.bank.exchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated

public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @GetMapping("/exchange/rates")
    public CurrencyRateResponse getRates() {
        return exchangeService.getPostsPlainJSONWithMap ();
    }


}
