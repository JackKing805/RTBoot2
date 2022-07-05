package com.rtboot.boot.rtboot.utils;

import com.rtboot.boot.rtboot.annotation.Value;
import com.rtboot.boot.rtboot.reflect.extend.CombinationAnnotationElement;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReflectUtils {
    public static boolean haveAnnotation(AnnotatedElement annotationEle, Class<? extends Annotation> annotationClass) {
        if (annotationEle == null || annotationClass == null) {
            return false;
        }
        return getAnnotation(annotationEle, annotationClass) != null;
    }

    public static <T extends Annotation> T getAnnotation(AnnotatedElement annotationEle, Class<T> annotationClass) {
        if (annotationEle == null || annotationClass == null) {
            return null;
        }
        CombinationAnnotationElement combinationAnnotationElement;
        if (annotationEle instanceof CombinationAnnotationElement) {
            combinationAnnotationElement = (CombinationAnnotationElement) annotationEle;
        } else {
            combinationAnnotationElement = new CombinationAnnotationElement(annotationEle);
        }
        return combinationAnnotationElement.getAnnotation(annotationClass);
    }


    /**
     * inInstance is error method
     * @param clazz
     * @return
     */
    @Deprecated()
    public static boolean isBasicTypesObject(Class<?> clazz) {
        if (
                clazz.isArray() ||
                        clazz.isInterface() ||
                        clazz.isEnum() ||
                        clazz==(Number.class) ||
                        clazz==(String.class) ||
                        clazz==(Integer.class) ||
                        clazz==(int.class) ||
                        clazz==(Boolean.class) ||
                        clazz==(boolean.class) ||
                        clazz==(Double.class) ||
                        clazz==(double.class) ||
                        clazz==(Float.class) ||
                        clazz==(float.class) ||
                        clazz==(Long.class) ||
                        clazz==(long.class) ||
                        clazz==(byte.class) ||
                        clazz==(short.class)
        ) {
            return true;
        }
        return false;
    }

    public static String getValueKey(Value value){
        Pattern regex = Pattern.compile("\\$\\{([^}]*)\\}");
        Matcher matcher = regex.matcher(value.value());
        StringBuilder sql = new StringBuilder();
        while(matcher.find()) {
            sql.append(matcher.group(1)).append(",");
        }
        if (sql.length() > 0) {
            sql.deleteCharAt(sql.length() - 1);
        }
        return sql.toString();
    }

    public static Object invokeMethod(Object clazz, Method method,Object...objects) throws InvocationTargetException, IllegalAccessException {
        if (clazz==null || method==null){
            return null;
        }
        return method.invoke(clazz, objects);
    }
}

