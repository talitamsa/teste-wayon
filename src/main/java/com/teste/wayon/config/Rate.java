package com.teste.wayon.config;

import java.math.BigDecimal;

public class Rate {
    private int start;
    private int end;
    private BigDecimal rate;

    public Rate(int start, int end, String rate) {
        this.start = start;
        this.end = end;
        this.rate = new BigDecimal(rate.replace("%", ""));
    }

    public boolean isInRange(int days) {
        return days >= start && days <= end;
    }

    public BigDecimal getRate() {
        return rate.divide(new BigDecimal("100"));
    }
}
