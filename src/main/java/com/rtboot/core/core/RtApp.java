package com.rtboot.core.core;

import com.rtboot.core.annotation.App;
import com.rtboot.core.bean.AppConfig;
import com.rtboot.core.factory.RtBeanFactory;
import com.rtboot.core.factory.RtClassFactory;
import com.rtboot.core.factory.RtInjectFactory;
import com.rtboot.core.factory.RtPropertiesFactory;
import com.rtboot.core.utils.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class RtApp {
    public static void run(Class<?> clazz){
        App app = clazz.getAnnotation(App.class);
        if(app!=null){
            Logger.i("RTBoot start success");
            AppConfig appConfig = new AppConfig(app.propertiesName());
            new RtApp(appConfig);
        }else {
            Logger.e("RTBoot start fail,reason is no App annotation");
        }
    }

    private RtApp(AppConfig config){
        RtClassFactory.registerAllClass();
        try {
            RtBeanFactory.registerBean();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            RtPropertiesFactory.initProperties(config.getPropertiesName());
        } catch (IOException e) {
            Logger.e("system properties load failure:"+e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
        try {
            RtInjectFactory.injectResources();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
