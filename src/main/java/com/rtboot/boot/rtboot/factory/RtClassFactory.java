package com.rtboot.boot.rtboot.factory;

import com.rtboot.boot.rtboot.loader.RTClassLoader;
import com.rtboot.boot.rtboot.utils.Logger;
import com.rtboot.boot.rtboot.utils.ReflectUtils;

import java.lang.annotation.Annotation;
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

    public static List<Class<?>> listClassesByAnnotation(Class<? extends Annotation> annotation){
        List<Class<?>> classes = new ArrayList<>();
        for (Class<?> aClass : classList) {
            if (ReflectUtils.haveAnnotation(aClass,annotation)){
                classes.add(aClass);
            }
        }
        return classes;
    }
}
