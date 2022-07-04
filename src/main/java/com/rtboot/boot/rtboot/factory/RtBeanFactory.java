package com.rtboot.boot.rtboot.factory;

import com.rtboot.boot.rtboot.annotation.Bean;
import com.rtboot.boot.rtboot.annotation.BeanQuery;
import com.rtboot.boot.rtboot.annotation.Controller;
import com.rtboot.boot.rtboot.annotation.Resource;
import com.rtboot.boot.rtboot.bean.MethodWrapper;
import com.rtboot.boot.rtboot.i.ObjectFindListener;
import com.rtboot.boot.rtboot.i.ParameterFindListener;
import com.rtboot.boot.rtboot.utils.Logger;
import com.rtboot.boot.rtboot.utils.ReflectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

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
            Object[] methodParameters = findMethodParameters(declaredMethod.getMethod());
            if (methodParameters==null){
                return false;
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
            Object methodReturn = ReflectUtils.invokeMethod(methodGroupClassInstance, declaredMethod.getMethod(), methodParameters);
            beanFactory.put(beanName,methodReturn);
        }
        return true;
    }

    public static Object[] findMethodParameters(Method method){
        Parameter[] parameters = method.getParameters();
        Object[] params = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Object bean = getParameterClassBean(parameters[i]);
            if (bean==null){
                return null;
            }
            params[i] = bean;
        }
        return params;
    }


    public static Object[] findMethodParameters(Method method, ParameterFindListener parameterFindListener){
        Parameter[] parameters = method.getParameters();
        Object[] params = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Object bean = getParameterClassBean(parameters[i]);
            if (bean==null){
                if(parameterFindListener!=null){
                    bean = parameterFindListener.findParameter(parameters[i]);
                    if (bean==null){
                        return null;
                    }
                }else {
                    return null;
                }
            }
            params[i] = bean;
        }
        return params;
    }

    public static Object getBean(Field field, ObjectFindListener objectFindListener){
        Resource resourceAnnotation = ReflectUtils.getAnnotation(field,Resource.class);
        Object bean;
        if (resourceAnnotation!=null && !resourceAnnotation.value().equals("")){
            bean = getBean(resourceAnnotation.value());
        }else {
            bean = RtBeanFactory.getClassBean(field.getType());
        }
        if (bean==null){
            if (objectFindListener!=null){
                bean = objectFindListener.findParameter(field.getType());
            }
        }
        return bean;
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

    /**
     * 查找含有相关注解的所有类
     */
    public static List<Object> listBeansByAnnotation(Class<? extends Annotation> annotation){
        List<Object> objects = new ArrayList<>();
        beanFactory.forEach((s, o) -> {
            if (ReflectUtils.haveAnnotation(o.getClass(),annotation)){
                objects.add(o);
            }
        });

        return objects;
    }

    public static List<Object> listBeans(){
        List<Object> objects = new ArrayList<>();
        beanFactory.forEach((s, o) -> {
            objects.add(o);
        });
        return objects;
    }
}
