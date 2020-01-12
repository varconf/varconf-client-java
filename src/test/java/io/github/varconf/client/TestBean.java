package io.github.varconf.client;

import io.github.varconf.client.annotation.VarConfValue;

public class TestBean {
    @VarConfValue("key")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
