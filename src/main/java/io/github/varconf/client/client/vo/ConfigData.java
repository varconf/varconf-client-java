package io.github.varconf.client.client.vo;

public class ConfigData {

    private Long configId;
    private Long appId;

    private String key;
    private String value;

    private Integer releaseIndex;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getReleaseIndex() {
        return releaseIndex;
    }

    public void setReleaseIndex(Integer releaseIndex) {
        this.releaseIndex = releaseIndex;
    }
}
