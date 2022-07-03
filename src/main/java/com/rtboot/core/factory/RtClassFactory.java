package com.rtboot.core.factory;

import com.rtboot.core.utils.Logger;
import com.rtboot.core.loader.RTClassLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源管理器
 */
public class RtClassFactory {
    /**
     * 项目中所有类成员
     */
    private static final List<Class<?>> classList = new ArrayList<>();

    public static void registerAllClass(){
        Logger.i("start to register All class");
        classList.addAll(RTClassLoader.loadClasses());
        for (Class<?> aClass : classList) {
            Logger.i("register class:"+aClass.getName());
        }
        Logger.i("end to register All class");
    }

    public static List<Class<?>> getProjectClassList(){
        return classList;
    }
}
