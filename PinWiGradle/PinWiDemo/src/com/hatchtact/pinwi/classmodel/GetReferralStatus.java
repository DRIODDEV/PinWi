package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

/**
 * Created by Ankur on 2/27/2017.
 */

public class GetReferralStatus implements Serializable
{

    private int ReferStatus;
    private String Message;
    private String IsOptForDontShowReferalCode;


    public int getReferStatus() {
        return ReferStatus;
    }

    public void setReferStatus(int referStatus) {
        ReferStatus = referStatus;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getIsOptForDontShowReferalCode() {
        return IsOptForDontShowReferalCode;
    }

    public void setIsOptForDontShowReferalCode(String isOptForDontShowReferalCode) {
        IsOptForDontShowReferalCode = isOptForDontShowReferalCode;
    }
}