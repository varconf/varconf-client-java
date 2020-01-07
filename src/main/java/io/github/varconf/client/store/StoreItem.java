package io.github.varconf.client.store;

public class StoreItem {

    private long timestamp;

    private String value;

    public StoreItem() {

    }

    public StoreItem(long timestamp, String value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
