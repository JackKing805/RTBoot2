package com.rtboot.core.core;


import com.rtboot.core.bean.AppConfig;
import com.rtboot.core.factory.RtBeanFactory;
import com.rtboot.core.factory.RtPropertiesFactory;
import com.rtboot.core.utils.PathUtils;

import java.net.URL;

public class RtContext {
    private AppConfig appConfig;

    public RtContext(AppConfig appConfig){
        this.appConfig = appConfig;
    }


    public <T> T getBean(String name){
        return RtBeanFactory.getBean(name);
    }

    public <T> T getBean(Class<?> clazz){
        return RtBeanFactory.getClassBean(clazz);
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

    public AppConfig getAppConfig(){
        return appConfig;
    }


}
