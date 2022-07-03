package com.rtboot.core.factory;

import com.rtboot.core.utils.Logger;
import com.rtboot.core.utils.PathUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;

/**
 * 配置加载 默认配置文件名称为 rt.properties
 */
public class RtPropertiesFactory {
    private static final Properties properties = new Properties();

    public static void initProperties(String propertiesName) throws IOException {
        Logger.i("start to initProperties propertiesName is " + propertiesName);
        InputStream inputStream = PathUtils.getUrl(propertiesName).openStream();
        properties.load(inputStream);
        inputStream.close();
        Logger.i("end to initProperties");
    }

    public static boolean hasProperty(String nodeName) {
        return properties.containsKey(nodeName);
    }

    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue) {
        String string = getString(key);
        if (!string.isEmpty()) {
            try {
                return Integer.parseInt(string);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    public static long getLong(String key) {
        return getLong(key, 0L);
    }

    public static long getLong(String key, long defaultValue) {
        String string = getString(key);
        if (!string.isEmpty()) {
            try {
                return Long.parseLong(string);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    public static float getFloat(String key) {
        return getFloat(key, 0f);
    }

    public static float getFloat(String key, float defaultValue) {
        String string = getString(key);
        if (!string.isEmpty()) {
            try {
                return Float.parseFloat(string);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    public static double getDouble(String key) {
        return getDouble(key, 0f);
    }

    public static double getDouble(String key, double defaultValue) {
        String string = getString(key);
        if (!string.isEmpty()) {
            try {
                return Double.parseDouble(string);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String string = getString(key);
        if (!string.isEmpty()) {
            try {
                return Boolean.parseBoolean(string);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }


//    public static <T> T loadBean(T t) {
//        Class<?> clazz = t.getClass();
//        String prefixName = clazz.getSimpleName().toLowerCase();
//        Field[] fields = clazz.getDeclaredFields();
//        for (Field field : fields) {
//            Class<?> nodeType = field.getType();
//            String nodeSuffix = field.getName().toLowerCase();
//            String nodeName = prefixName + "." + nodeSuffix;
//            if (nodeType.isInstance(String.class)) {
//                invokeSet(t, nodeSuffix, getString(nodeName));
//            } else if (nodeType.isInstance(int.class)) {
//                invokeSet(t, nodeSuffix, getInt(nodeName));
//            } else if (nodeType.isInstance(long.class)) {
//                invokeSet(t, nodeSuffix, getLong(nodeName));
//            } else if (nodeType.isInstance(boolean.class)) {
//                invokeSet(t, nodeSuffix, getBoolean(nodeName));
//            } else if (nodeType.isInstance(float.class)) {
//                invokeSet(t, nodeSuffix, getFloat(nodeName));
//            } else if (nodeType.isInstance(double.class)) {
//                invokeSet(t, nodeSuffix, getDouble(nodeName));
//            }
//        }
//        return t;
//    }
//
//    private static Method getSetMethod(Class<?> objectClass, String fieldName) {
//        try {
//            Class<?>[] parameterTypes = new Class[1];
//            Field field = objectClass.getDeclaredField(fieldName);
//            parameterTypes[0] = field.getType();
//            String sb = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
//            return objectClass.getMethod(sb, parameterTypes);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        throw new NoSuchMethodError("you must create set method for properties " + fieldName);
//    }
//
//    private static void invokeSet(Object o, String fieldName, Object value) {
//        Method method = getSetMethod(o.getClass(), fieldName);
//        try {
//            method.invoke(o, value);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
