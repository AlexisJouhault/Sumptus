package com.frusby.sumptusmagnus.core;

import com.orm.SugarRecord;

/**
 * Created by alexisjouhault on 5/14/16.
 */
public class Receipt extends SugarRecord {

    private String value;
    private String issuer;

    public Receipt(String issuer, String value) {
        this.issuer = issuer;
        this.value = value;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
