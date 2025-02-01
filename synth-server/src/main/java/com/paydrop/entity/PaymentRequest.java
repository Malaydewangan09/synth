package com.paydrop.entity;

import java.math.BigDecimal;


public class PaymentRequest {
    private BigDecimal testamount;

    public BigDecimal getTestamount() {
        return testamount;
    }

    public void setTestamount(BigDecimal testamount) {
        this.testamount = testamount;
    }
}