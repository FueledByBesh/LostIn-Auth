package com.lostin.auth.model.core.client;

public enum ClientType {
    PUBLIC("public"),
    CONFIDENTIAL("confidential");

    public final String value;

    ClientType(String value){
        this.value=value;
    }
}
