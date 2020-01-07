package io.github.varconf.client.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class StoreManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConcurrentMap<String, StoreItem> storeMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, List<BeanItem>> beanListMap = new ConcurrentHashMap<>();

    public void setValue(String key, String value) {
        storeMap.put(key, new StoreItem(System.currentTimeMillis(), value));
        this.setBeanFiledValue(key, value);
    }

    public String getValue(String key) {
        StoreItem cacheItem = storeMap.get(key);
        if (cacheItem != null) {
            return cacheItem.getValue();
        }
        return null;
    }

    public void putBean(String key, Field field, Object bean) {
        List<BeanItem> beanItemList = beanListMap.get(key);
        if (beanItemList == null) {
            beanItemList = new ArrayList<>();
            beanListMap.put(key, beanItemList);
        }
        beanItemList.add(new BeanItem(field, bean));
    }

    private void setBeanFiledValue(String key, String value) {
        List<BeanItem> beanItemList = beanListMap.get(key);
        if (beanItemList == null) {
            return;
        }

        for (BeanItem beanItem : beanItemList) {
            try {
                Field field = beanItem.getField();
                field.setAccessible(true);

                String type = field.getGenericType().toString();
                if (type.equals("class java.lang.String")) {
                    field.set(beanItem.getBean(), value);
                } else if (type.equals("class java.lang.Boolean")) {
                    field.setBoolean(beanItem.getBean(), Boolean.valueOf(value));
                } else if (type.equals("class java.lang.Integer")) {
                    field.setInt(beanItem.getBean(), Integer.valueOf(value));
                } else if (type.equals("class java.lang.Long")) {
                    field.setLong(beanItem.getBean(), Long.valueOf(value));
                } else if (type.equals("class java.lang.Float")) {
                    field.setFloat(beanItem.getBean(), Float.valueOf(value));
                } else if (type.equals("class java.lang.Double")) {
                    field.setDouble(beanItem.getBean(), Double.valueOf(value));
                } else {
                    field.set(beanItem.getBean(), value);
                }
            } catch (Exception e) {
                logger.error("field set error!", e);
            }
        }
    }
}
