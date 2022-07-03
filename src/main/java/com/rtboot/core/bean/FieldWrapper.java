package com.rtboot.core.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FieldWrapper {
    private int tryInvokeTimes = 0;
    private Class<?> aClass;
    private Field field;

    public FieldWrapper(Class<?> aClass, Field field) {
        this.aClass = aClass;
        this.field = field;
    }

    public Class<?> getaClass() {
        return aClass;
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
