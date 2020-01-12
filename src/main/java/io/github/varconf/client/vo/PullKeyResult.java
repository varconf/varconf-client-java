package io.github.varconf.client.vo;

public class PullKeyResult {
    private ConfigValue data;

    private Integer recentIndex;

    public ConfigValue getData() {
        return data;
    }

    public void setData(ConfigValue data) {
        this.data = data;
    }

    public Integer getRecentIndex() {
        return recentIndex;
    }

    public void setRecentIndex(Integer recentIndex) {
        this.recentIndex = recentIndex;
    }
}
