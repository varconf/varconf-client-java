package io.github.varconf.client.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BeanManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConcurrentMap<String, List<BeanFiledItem>> beanListMap = new ConcurrentHashMap<>();

    public void putBeanFiled(String key, Object bean, Field field) {
        List<BeanFiledItem> itemList = beanListMap.get(key);
        if (itemList == null) {
            itemList = new ArrayList<>();
            beanListMap.put(key, itemList);
        }
        itemList.add(new BeanFiledItem(bean, field));
    }

    public void setBeanFiledValue(String key, String value) {
        List<BeanFiledItem> itemList = beanListMap.get(key);
        if (itemList == null) {
            return;
        }

        for (BeanFiledItem item : itemList) {
            try {
                Field field = item.getField();
                field.setAccessible(true);

                String type = field.getGenericType().toString();
                if (type.equals("class java.lang.String")) {
                    field.set(item.getBean(), value);
                } else if (type.equals("class java.lang.Boolean")) {
                    field.setBoolean(item.getBean(), Boolean.valueOf(value));
                } else if (type.equals("class java.lang.Integer")) {
                    field.setInt(item.getBean(), Integer.valueOf(value));
                } else if (type.equals("class java.lang.Long")) {
                    field.setLong(item.getBean(), Long.valueOf(value));
                } else if (type.equals("class java.lang.Float")) {
                    field.setFloat(item.getBean(), Float.valueOf(value));
                } else if (type.equals("class java.lang.Double")) {
                    field.setDouble(item.getBean(), Double.valueOf(value));
                } else {
                    field.set(item.getBean(), value);
                }
            } catch (Exception e) {
                logger.error("field set error!", e);
            }
        }
    }

    private class BeanFiledItem {
        private Object bean;

        private Field field;

        public BeanFiledItem(Object bean, Field field) {
            this.bean = bean;
            this.field = field;
        }

        public Object getBean() {
            return bean;
        }

        public void setBean(Object bean) {
            this.bean = bean;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }
    }
}
