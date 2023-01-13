package com.adrian.bank.exchange;

import lombok.Data;

import java.util.Map;
@Data // pravi avtomatichni constructori, getteri, setteri
public class CurrencyRateResponse {

    Map<String, Double> data;

//    public CurrencyRateResponse() {
//    }
//
//    public Map<String, Double> getData() {
//        return data;
//    }
//
//    public void setData(Map<String, Double> data) {
//        this.data = data;
//    }

}
