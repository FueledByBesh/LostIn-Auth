package com.lostin.auth.repository.impl.cache;

import lombok.Getter;

@Getter
public enum CachingOption {
    /// saves email as a key and id as value
    USER_EMAIL_TO_ID("email:id-");

    private final String prefix;
    CachingOption(String prefix) {
        this.prefix = prefix;
    }

    public String getKey(String key){
        return prefix + key;
    }
}
