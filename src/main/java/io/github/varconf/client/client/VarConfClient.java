package io.github.varconf.client.client;

import com.alibaba.fastjson.JSON;
import io.github.varconf.client.annotation.VarConfValue;
import io.github.varconf.client.client.vo.ConfigData;
import io.github.varconf.client.store.StoreManager;
import io.github.varconf.client.util.HttpUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class VarConfClient implements BeanPostProcessor, InitializingBean {

    private String url;
    private String token;
    private Set<String> keySet = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    private StoreManager storeManager = new StoreManager();

    public void setUrl(String url) {
        this.url = url;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getValue(String key) {
        return storeManager.getValue(key);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            VarConfValue varValue = f.getAnnotation(VarConfValue.class);
            if (varValue != null) {
                keySet.add(varValue.value());
                storeManager.putBean(varValue.value(), f, bean);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (String key : keySet) {
            StringBuilder keyPath = new StringBuilder();
            keyPath.append(url).append("/api/config/").append(key);
            keyPath.append("?token=").append(token);

            String result = HttpUtil.get(keyPath.toString());
            if (result != null) {
                ConfigData configData = JSON.parseObject(result, ConfigData.class);
                if (configData != null) {
                    storeManager.setValue(key, configData.getValue());
                }
            }
        }
    }
}
