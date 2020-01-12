package io.github.varconf.client;

import com.alibaba.fastjson.JSON;
import io.github.varconf.client.annotation.VarConfValue;
import io.github.varconf.client.store.BeanManager;
import io.github.varconf.client.vo.ConfigValue;
import io.github.varconf.client.vo.PullAppResult;
import io.github.varconf.client.vo.PullKeyResult;
import io.github.varconf.client.store.StoreManager;
import io.github.varconf.client.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

public class VarConfClient implements BeanPostProcessor, InitializingBean, DisposableBean, Runnable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String url;

    private String token;

    private Integer errorSleep = 5;

    private BeanManager beanManager = new BeanManager();

    private StoreManager storeManager = new StoreManager();

    private boolean running = true;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setErrorSleep(Integer errorSleep) {
        this.errorSleep = errorSleep;
    }

    public PullAppResult queryAppConfig(boolean longPull, int lastIndex) throws IOException {
        StringBuilder requestPath = new StringBuilder();
        requestPath.append(url).append("/api/config");
        requestPath.append("?token=").append(token);
        if (longPull) {
            requestPath.append("&longPull=true&lastIndex=").append(lastIndex);
        }

        String result = HttpUtil.get(requestPath.toString());
        if (result != null) {
            return JSON.parseObject(result, PullAppResult.class);
        }

        return null;
    }

    public PullKeyResult queryKeyConfig(String key, boolean longPull, int lastIndex) throws IOException {
        StringBuilder requestPath = new StringBuilder();
        requestPath.append(url).append("/api/config/").append(key);
        requestPath.append("?token=").append(token);
        if (longPull) {
            requestPath.append("&longPull=true&lastIndex=").append(lastIndex);
        }

        String result = HttpUtil.get(requestPath.toString());
        if (result != null) {
            return JSON.parseObject(result, PullKeyResult.class);
        }

        return null;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            VarConfValue varValue = f.getAnnotation(VarConfValue.class);
            if (varValue != null) {
                String key = varValue.value();
                beanManager.putBeanFiled(key, bean, f);

                String value = storeManager.getValue(key);
                if (value != null) {
                    beanManager.setBeanFiledValue(key, value);
                }
            }
        }
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        refreshAppConfig(false, 0);
        new Thread(this).start();
    }

    @Override
    public void destroy() throws Exception {
        running = false;
    }

    @Override
    public void run() {
        int lastIndex = 0;
        while (running) {
            try {
                PullAppResult pullAppResult = refreshAppConfig(true, lastIndex);
                if (pullAppResult == null) {
                    continue;
                }
                lastIndex = pullAppResult.getRecentIndex();
            } catch (IOException e) {
                logger.error("pull app config error!", e);

                try {
                    Thread.sleep(errorSleep * 1000);
                } catch (InterruptedException ex) {
                    logger.error("sleep error!", e);
                }
            }
        }
    }

    private PullAppResult refreshAppConfig(boolean longPull, int lastIndex) throws IOException {
        PullAppResult pullAppResult = this.queryAppConfig(longPull, lastIndex);
        if (pullAppResult == null) {
            return null;
        }

        Map<String, ConfigValue> data = pullAppResult.getData();
        if (data == null) {
            return null;
        }

        for (ConfigValue configValue : data.values()) {
            String key = configValue.getKey();
            String value = configValue.getValue();

            storeManager.setValue(key, value);
            beanManager.setBeanFiledValue(key, value);
        }

        return pullAppResult;
    }
}
