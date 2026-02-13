package com.lostin.auth.repository.impl.cache;

import lombok.Getter;

@Getter
public enum CachingOption {
    /// saves email as a key and id as value
    USER_EMAIL_TO_ID("email:id-"),
    OPAQUE_TOKEN_TO_UID("opaque_token:uid-");

    private final String prefix;
    CachingOption(String prefix) {
        this.prefix = prefix;
    }

    public String getKey(String key){
        return prefix + key;
    }
}
