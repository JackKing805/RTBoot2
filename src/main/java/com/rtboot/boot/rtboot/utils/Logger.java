package com.rtboot.boot.rtboot.utils;

import java.text.SimpleDateFormat;

public class Logger {
    public static SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//    [31;0m 恢复样式
    public static void e(String msg){
//        2021-12-07 22:48:39.337  INFO 18268 --- [           main] c.p.jcloudpan.JCloudPanApplication       : The following profiles are active: dev
        StackTraceElement stackTraceElement = new Exception().getStackTrace()[1];
        String runLocation = "<"+stackTraceElement.getLineNumber()+"> "+stackTraceElement.getClassName()+"."+stackTraceElement.getMethodName();

        System.out.format("%s \33[31;1mERROR \33[31;0m--- [          %s] \33[32;1m%s \33[31;0m: %s %n", sd.format(System.currentTimeMillis()),Thread.currentThread().getName(),runLocation,msg);
    }

    public static void i(String msg){
//        2021-12-07 22:48:39.337  INFO 18268 --- [           main] c.p.jcloudpan.JCloudPanApplication       : The following profiles are active: dev
        StackTraceElement stackTraceElement = new Exception().getStackTrace()[1];
        String runLocation = "<"+stackTraceElement.getLineNumber()+"> "+stackTraceElement.getClassName()+"."+stackTraceElement.getMethodName();

        System.out.format("%s \33[33;1mINFO  \33[31;0m--- [          %s] \33[32;1m%s \33[31;0m: %s  %n", sd.format(System.currentTimeMillis()),Thread.currentThread().getName(),runLocation,msg);
    }
}
