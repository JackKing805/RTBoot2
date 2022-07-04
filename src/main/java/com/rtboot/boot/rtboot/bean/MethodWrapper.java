package com.rtboot.boot.rtboot.bean;

import java.lang.reflect.Method;

public class MethodWrapper {
    private int tryInvokeTimes = 0;
    private Class<?> aClass;
    private Method method;

    public MethodWrapper(Class<?> aClass, Method method) {
        this.aClass = aClass;
        this.method = method;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    public Method getMethod() {
        return method;
    }

    public boolean canTry(){
        int times = tryInvokeTimes;
        tryInvokeTimes++;
        return times<2;
    }
}
