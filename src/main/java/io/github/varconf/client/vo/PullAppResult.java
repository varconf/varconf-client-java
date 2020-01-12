package io.github.varconf.client.vo;

import java.util.Map;

public class PullAppResult {
    private Map<String, ConfigValue> data;

    private Integer recentIndex;

    public Map<String, ConfigValue> getData() {
        return data;
    }

    public void setData(Map<String, ConfigValue> data) {
        this.data = data;
    }

    public Integer getRecentIndex() {
        return recentIndex;
    }

    public void setRecentIndex(Integer recentIndex) {
        this.recentIndex = recentIndex;
    }
}
