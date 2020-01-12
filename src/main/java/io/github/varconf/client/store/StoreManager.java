package io.github.varconf.client.store;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class StoreManager {
    private final ConcurrentMap<String, String> configMap = new ConcurrentHashMap<>();

    public void setValue(String key, String value) {
        configMap.put(key, value);
    }

    public String getValue(String key) {
        return configMap.get(key);
    }
}
