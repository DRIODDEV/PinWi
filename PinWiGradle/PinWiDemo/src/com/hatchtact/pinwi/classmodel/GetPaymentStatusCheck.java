package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

/**
 * Created by Ankur on 2/27/2017.
 */

public class GetPaymentStatusCheck implements Serializable
{

    private int PaymentStatus;


    public int getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        PaymentStatus = paymentStatus;
    }
}