package com.rtboot.core.factory;

import com.rtboot.core.annotation.Resource;
import com.rtboot.core.annotation.Value;
import com.rtboot.core.bean.FieldWrapper;
import com.rtboot.core.utils.Logger;
import com.rtboot.core.utils.ReflectUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RtInjectFactory {
    public static void injectResources() throws IllegalAccessException {
        Logger.i("start to inject resource");
        injectFields();
        injectPropertiesValue();
        Logger.i("end to inject resource");
    }

    private static void injectFields() throws IllegalAccessException {
        List<FieldWrapper> fieldWrapperList = new ArrayList<>();
        List<Class<?>> projectClassList = RtClassFactory.getProjectClassList();
        for (Class<?> aClass : projectClassList) {
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                Resource resourceAnnotation = ReflectUtils.getAnnotation(declaredField,Resource.class);
                if (resourceAnnotation!=null){
                    fieldWrapperList.add(new FieldWrapper(aClass,declaredField));
                }
            }
        }
        for (FieldWrapper fieldWrapper : fieldWrapperList) {
            if(!injectField(fieldWrapper)){
                throw new IllegalAccessException(fieldWrapper.getField().getType().getName()+" not inject in beanFactory");
            }else {
                Logger.i("inject resource in class:"+fieldWrapper.getaClass().getName()+",fieldType:"+fieldWrapper.getField().getType().getName()+",filed:"+ fieldWrapper.getField().getName());
            }
        }
    }

    private static boolean injectField(FieldWrapper fieldWrapper){
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
            return false;
        }
        Object fieldGroupClassBean = RtBeanFactory.getClassBean(fieldWrapper.getaClass());
        if (fieldGroupClassBean==null){
            return false;
        }

        try {
            boolean isHidden = fieldWrapper.getField().isAccessible();
            fieldWrapper.getField().setAccessible(true);
            fieldWrapper.getField().set(fieldGroupClassBean,bean);
            if(!isHidden){
                fieldWrapper.getField().setAccessible(false);
            }
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
        List<Class<?>> projectClassList = RtClassFactory.getProjectClassList();
        for (Class<?> aClass : projectClassList) {
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                Value resourceAnnotation = ReflectUtils.getAnnotation(declaredField, Value.class);
                if (resourceAnnotation!=null){
                    fieldWrapperList.add(new FieldWrapper(aClass,declaredField));
                }
            }
        }
        for (FieldWrapper fieldWrapper : fieldWrapperList) {
            Value annotation = fieldWrapper.getField().getAnnotation(Value.class);
            if(annotation!=null){
                String value = ReflectUtils.getValueKey(annotation);
                String result = RtPropertiesFactory.getString(value);
                Object classBean = RtBeanFactory.getClassBean(fieldWrapper.getaClass());
                if (!value.equals("") && result!=null&&classBean!=null) {
                    Logger.i("inject value in class:"+fieldWrapper.getaClass().getName()+",fieldType:"+fieldWrapper.getField().getType().getName()+",value:"+ value+",isHidden:"+fieldWrapper.getField().isAccessible());
                    boolean isHidden = fieldWrapper.getField().isAccessible();
                    fieldWrapper.getField().setAccessible(true);
                    fieldWrapper.getField().set(classBean,result);
                    if(!isHidden){
                        fieldWrapper.getField().setAccessible(false);
                    }
                }
            }
        }
    }

}
