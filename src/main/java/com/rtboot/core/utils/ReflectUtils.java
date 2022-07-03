package com.rtboot.core.utils;

import com.rtboot.core.annotation.Value;
import com.rtboot.core.reflect.extend.CombinationAnnotationElement;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.math.BigDecimal;
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


    public static boolean isBasicTypesObject(Class<?> clazz) {
        if (
                clazz.isArray() ||
                        clazz.isInterface() ||
                        clazz.isEnum() ||
                        clazz.isInstance(Number.class) ||
                        clazz.isInstance(String.class) ||
                        clazz.isInstance(Integer.class) ||
                        clazz.isInstance(int.class) ||
                        clazz.isInstance(Boolean.class) ||
                        clazz.isInstance(boolean.class) ||
                        clazz.isInstance(Double.class) ||
                        clazz.isInstance(double.class) ||
                        clazz.isInstance(Float.class) ||
                        clazz.isInstance(float.class) ||
                        clazz.isInstance(Long.class) ||
                        clazz.isInstance(long.class) ||
                        clazz.isInstance(byte.class) ||
                        clazz.isInstance(short.class)
        ) {
            return false;
        }
        return true;
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
}

