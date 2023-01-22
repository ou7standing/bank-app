package com.adrian.bank.exchange;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class CurrencyRateResponse {

    Map<String, BigDecimal> data;


}
