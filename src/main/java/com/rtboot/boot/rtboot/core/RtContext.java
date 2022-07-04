package com.rtboot.boot.rtboot.core;


import com.rtboot.boot.rtboot.annotation.Resource;
import com.rtboot.boot.rtboot.bean.AppConfig;
import com.rtboot.boot.rtboot.factory.RtBeanFactory;
import com.rtboot.boot.rtboot.factory.RtClassFactory;
import com.rtboot.boot.rtboot.factory.RtPropertiesFactory;
import com.rtboot.boot.rtboot.i.ObjectFindListener;
import com.rtboot.boot.rtboot.i.ParameterFindListener;
import com.rtboot.boot.rtboot.utils.PathUtils;
import com.rtboot.boot.rtboot.utils.ReflectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RtContext {
    private final AppConfig appConfig;

    public RtContext(AppConfig appConfig){
        this.appConfig = appConfig;
    }


    public <T> T getBean(String name){
        return RtBeanFactory.getBean(name);
    }

    public <T> T getBean(Class<?> clazz){
        return RtBeanFactory.getClassBean(clazz);
    }
    public List<Object> getBeans(Class<? extends Annotation> clazz){
        return RtBeanFactory.listBeansByAnnotation(clazz);
    }

    public Object[] findMethodParameters(Method method){
        return RtBeanFactory.findMethodParameters(method);
    }

    public Object[] findMethodParameters(Method method, ParameterFindListener parameterFindListener){
        return RtBeanFactory.findMethodParameters(method,parameterFindListener);
    }

    public Object getBean(Field field, ObjectFindListener objectFindListener){
        return RtBeanFactory.getBean(field,objectFindListener);
    }

    public List<Class<?>> getProjectClassList(){
        return RtClassFactory.getProjectClassList();
    }

    public List<Class<?>> listClassesByAnnotation(Class<? extends Annotation> annotation){
        return RtClassFactory.listClassesByAnnotation(annotation);
    }

    public String getStringProperties(String key ,String defaultValue){
        return RtPropertiesFactory.getString(key,defaultValue);
    }

    public int getIntProperties(String key ,int defaultValue){
        return RtPropertiesFactory.getInt(key,defaultValue);
    }

    public long getLongProperties(String key ,long defaultValue){
        return RtPropertiesFactory.getLong(key,defaultValue);
    }

    public float getFloatProperties(String key ,float defaultValue){
        return RtPropertiesFactory.getFloat(key,defaultValue);
    }

    public double getDoubleProperties(String key ,double defaultValue){
        return RtPropertiesFactory.getDouble(key,defaultValue);
    }

    public boolean getBooleanProperties(String key ,boolean defaultValue){
        return RtPropertiesFactory.getBoolean(key,defaultValue);
    }

    public URL getResources(String fileName){
        return PathUtils.getUrl(fileName);
    }

    public String getProjectPath(){
        return PathUtils.getProjectPath();
    }

    public AppConfig getAppConfig(){
        return appConfig;
    }


}
