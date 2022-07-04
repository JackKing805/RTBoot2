package com.rtboot.boot.rtboot.bean;

import java.lang.reflect.Field;

public class FieldWrapper {
    private int tryInvokeTimes = 0;
    private Object object;
    private Field field;

    public FieldWrapper(Object object, Field field) {
        this.object = object;
        this.field = field;
    }

    public Object getObject() {
        return object;
    }

    public Field getField() {
        return field;
    }

    public boolean canTry(){
        int times = tryInvokeTimes;
        tryInvokeTimes++;
        return times<2;
    }
}
