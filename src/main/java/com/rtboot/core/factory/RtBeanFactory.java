package com.rtboot.core.factory;

import com.rtboot.core.annotation.Bean;
import com.rtboot.core.annotation.BeanQuery;
import com.rtboot.core.bean.MethodWrapper;
import com.rtboot.core.utils.Logger;
import com.rtboot.core.utils.ReflectUtils;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RtBeanFactory {
    /**
     * key 值如果Bean注解value不为空，则未value的值，否则使用类的Name
     */
    private static final Map<String,Object> beanFactory = new HashMap<String,Object>();

    public static void registerBean() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Logger.i("start to register beanFactory");
        registerClassBean();
        registerMethodBean();
        for (Map.Entry<String, Object> stringObjectEntry : beanFactory.entrySet()) {
            Logger.i("register bean,key:"+stringObjectEntry.getKey()+",value:"+stringObjectEntry.getValue());
        }
        Logger.i("end to register beanFactory");
    }

    private static void registerClassBean() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Class<?> aClass : RtClassFactory.getProjectClassList()) {
            Bean beanAnnotation = ReflectUtils.getAnnotation(aClass, Bean.class);
            if (beanAnnotation!=null){
                String value = beanAnnotation.value();
                String beanName = aClass.getName();
                if(value!=null && !value.equals("")){
                    beanName = value;
                }
                Object o = aClass.getDeclaredConstructor().newInstance();
                beanFactory.put(beanName,o);
            }
        }
    }

    /**
     * 注册方法返回的bean
     */
    private static void registerMethodBean() throws InvocationTargetException, IllegalAccessException {
        List<MethodWrapper> beanMethodWrapperList = new ArrayList<>();
        for (Class<?> aClass : RtClassFactory.getProjectClassList()) {
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                Bean beanAnnotation = ReflectUtils.getAnnotation(declaredMethod, Bean.class);
                if(beanAnnotation!=null){
                    beanMethodWrapperList.add(new MethodWrapper(aClass,declaredMethod));
                }
            }
        }
        registerMethodBean(beanMethodWrapperList);
    }

    private static void registerMethodBean(List<MethodWrapper> beanMethodWrapperList) throws InvocationTargetException, IllegalAccessException {
        List<MethodWrapper> continueBeanMethodWrapperList = new ArrayList<>();
        for (MethodWrapper declaredMethod : beanMethodWrapperList) {
            if (!registerMethodBean(declaredMethod)){
                if (declaredMethod.canTry()){
                    continueBeanMethodWrapperList.add(declaredMethod);
                }else {
                    throw new IllegalArgumentException("inject bean "+declaredMethod.getMethod().getReturnType().getName()+" failure");
                }
            }
        }
        if(!continueBeanMethodWrapperList.isEmpty()){
            registerMethodBean(continueBeanMethodWrapperList);
        }
    }

    private static boolean registerMethodBean(MethodWrapper declaredMethod) throws InvocationTargetException, IllegalAccessException {
        Bean beanAnnotation = ReflectUtils.getAnnotation(declaredMethod.getMethod(), Bean.class);
        if (beanAnnotation!=null){
            Parameter[] parameters = declaredMethod.getMethod().getParameters();
            Object[] params = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Object bean = getParameterClassBean(parameters[i]);
                if (bean==null){
                    return false;
                }
                params[i] = bean;
            }

            String value = beanAnnotation.value();
            String beanName = declaredMethod.getMethod().getReturnType().getName();
            if(value!=null && !value.equals("")){
                beanName = value;
            }
            Object methodGroupClassInstance = getClassBean(declaredMethod.getaClass());
            if(methodGroupClassInstance==null){
                throw new IllegalArgumentException(declaredMethod.getaClass().getName()+"'s bean not inject");
            }
            Object methodReturn = declaredMethod.getMethod().invoke(methodGroupClassInstance,params);
            beanFactory.put(beanName,methodReturn);
        }
        return true;
    }









    /**
     * name :全类名（getName()） 或者 bean注解的value值
     */
    public static <T> T getBean(String name){
        return (T) beanFactory.get(name);
    }

    public static <T> T getClassBean(Class<?> clazz){
        String beanName = clazz.getName();
        Bean beanAnnotation = ReflectUtils.getAnnotation(clazz, Bean.class);
        if (beanAnnotation!=null){
            String value = beanAnnotation.value();
            if(value!=null && !value.equals("")){
                beanName = value;
            }
        }
        return getBean(beanName);
    }

    /**
     * 查询参数注解
     */
    public static <T> T getParameterClassBean(Parameter parameter){
        BeanQuery beanQueryAnnotation = ReflectUtils.getAnnotation(parameter, BeanQuery.class);
        if (beanQueryAnnotation!=null){
            return getBean(beanQueryAnnotation.value());
        }else {
            Class<?> type = parameter.getType();
            return getClassBean(type);
        }
    }
}
