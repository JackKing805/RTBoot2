package com.rtboot.boot.rtboot.factory;

import com.rtboot.boot.rtboot.annotation.Resource;
import com.rtboot.boot.rtboot.annotation.Value;
import com.rtboot.boot.rtboot.bean.FieldWrapper;
import com.rtboot.boot.rtboot.i.ObjectFindListener;
import com.rtboot.boot.rtboot.utils.Logger;
import com.rtboot.boot.rtboot.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class RtInjectFactory {
    public static void injectResources() throws IllegalAccessException {
        Logger.i("start to inject resource");
        injectFields();
        injectPropertiesValue();
        Logger.i("end to inject resource");
    }

    private static void injectFields() throws IllegalAccessException {
        List<FieldWrapper> fieldWrapperList = new ArrayList<>();
        List<Object> objects = RtBeanFactory.listBeans();
        for (Object object: objects) {
            Class<?> aClass = object.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                Resource resourceAnnotation = ReflectUtils.getAnnotation(declaredField,Resource.class);
                if (resourceAnnotation!=null){
                    fieldWrapperList.add(new FieldWrapper(object,declaredField));
                }
            }
        }
        for (FieldWrapper fieldWrapper : fieldWrapperList) {
            if(!injectField(fieldWrapper,null)){
                throw new IllegalAccessException(fieldWrapper.getField().getType().getName()+" not inject in beanFactory");
            }else {
                Logger.i("inject resource in class:"+fieldWrapper.getObject().getClass().getName()+",fieldType:"+fieldWrapper.getField().getType().getName()+",filed:"+ fieldWrapper.getField().getName());
            }
        }
    }

    private static boolean injectField(FieldWrapper fieldWrapper,ObjectFindListener objectFindListener){
        Resource resourceAnnotation = ReflectUtils.getAnnotation(fieldWrapper.getField(),Resource.class);
        if (resourceAnnotation==null){
            return false;
        }
        Object bean;
        if (!resourceAnnotation.value().equals("")){
            bean = RtBeanFactory.getBean(resourceAnnotation.value());
        }else {
            bean = RtBeanFactory.getClassBean(fieldWrapper.getField().getType());
        }
        if (bean==null){
            if(objectFindListener!=null){
                bean=objectFindListener.findParameter(fieldWrapper.getField().getType());
                if (bean==null){
                    return false;
                }
            }else {
                return false;
            }
        }

        try {
            ReflectUtils.injectField(fieldWrapper.getObject(),fieldWrapper.getField(),bean);
            return true;
        } catch (IllegalAccessException e) {
            return false;
        }
    }


    /**
     * 注入配置文件的值
     */
    private static void injectPropertiesValue() throws IllegalAccessException {
        List<FieldWrapper> fieldWrapperList = new ArrayList<>();
        List<Object> objects = RtBeanFactory.listBeans();
        for (Object object : objects) {
            Class<?> aClass = object.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                Value resourceAnnotation = ReflectUtils.getAnnotation(declaredField, Value.class);
                if (resourceAnnotation!=null){
                    fieldWrapperList.add(new FieldWrapper(object,declaredField));
                }
            }
        }
        for (FieldWrapper fieldWrapper : fieldWrapperList) {
            injectPropertiesValue(fieldWrapper);
        }
    }

    private static void injectPropertiesValue(FieldWrapper fieldWrapper) throws IllegalAccessException {
        Value annotation = fieldWrapper.getField().getAnnotation(Value.class);
        if(annotation!=null){
            String value = ReflectUtils.getValueKey(annotation);
            String result = RtPropertiesFactory.getString(value);
            if (!value.equals("") && result!=null) {
                Logger.i("inject value in class:"+fieldWrapper.getObject().getClass().getName()+",fieldType:"+fieldWrapper.getField().getType().getName()+",value:"+ value+",isHidden:"+fieldWrapper.getField().isAccessible());
                ReflectUtils.injectField(fieldWrapper.getObject(),fieldWrapper.getField(),result);
            }
        }
    }

    public static void injectObject(Object object, ObjectFindListener findListener) throws IllegalAccessException {
        if (object==null){
            return;
        }
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            FieldWrapper fieldWrapper = new FieldWrapper(object, declaredField);
            injectField(fieldWrapper,findListener);
            injectPropertiesValue(fieldWrapper);
        }

    }

}
