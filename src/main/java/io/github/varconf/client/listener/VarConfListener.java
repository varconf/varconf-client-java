package io.github.varconf.client.listener;

public interface VarConfListener {
    void onUpdate(String key, Object value);
}
