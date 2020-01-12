# varconf-client-java
> 基于java语言的varconf客户端sdk.

![](https://img.shields.io/badge/language-java-cccfff.svg)

## 使用步骤
`1.Spring中xml配置客户端（能够自动注入Spring上下文配置）`
```java
    VarConfClient varConfClient = new VarConfClient();
    varConfClient.setUrl("varconf url");
    varConfClient.setToken("your app token");
```
`2.手动配置客户端（只能手动拉取key）`
```xml
    <bean id="varConfClient" class="io.github.varconf.client.VarConfClient" lazy-init="true">
        <property name="url" value="varconf url"></property>
        <property name="token" value="your app token"></property>
    </bean>
```

## 使用示例
`1.使用注解`
```java
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
```

`2.手动获取`
```java
    PullKeyResult pullKeyResult = varConfClient.queryKeyConfig("key", false, 0);
```

