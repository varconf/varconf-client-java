package io.github.varconf.client.store;

import java.lang.reflect.Field;

public class BeanItem {
    private Field field;

    private Object bean;

    public BeanItem(Field field, Object bean) {
        this.field = field;
        this.bean = bean;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
