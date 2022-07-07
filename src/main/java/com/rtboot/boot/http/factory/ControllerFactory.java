package com.rtboot.boot.http.factory;

import com.rtboot.boot.http.factory.model.ControllerMapper;
import com.rtboot.boot.http.annotation.Controller;
import com.rtboot.boot.rtboot.core.RtContext;
import com.rtboot.boot.rtboot.utils.Logger;
import com.rtboot.boot.rtboot.utils.ReflectUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ControllerFactory {
    private static final List<ControllerMapper> controllerMapperList = new ArrayList<>();

    public static void prepareController(RtContext rtContext){
        Logger.i("ControllerFactory init start");
        initControllerMappers(rtContext);
        Logger.i("ControllerFactory init complete");

    }

    private static void initControllerMappers(RtContext rtContext){
//        List<String> allFullPath = new ArrayList<>();
//        List<String> allControllerPath = new ArrayList<>();

        List<Class<?>> classes = rtContext.listClassesByAnnotation(Controller.class);
        for (Class<?> clazz : classes) {
            Controller claszzAnnotation = ReflectUtils.getAnnotation(clazz, Controller.class);
            if (claszzAnnotation!=null){
//                allControllerPath.add(claszzAnnotation.value());
                Method[] declaredMethods = clazz.getDeclaredMethods();
                for (Method declaredMethod : declaredMethods) {
                    Controller methodAnnotation = ReflectUtils.getAnnotation(declaredMethod, Controller.class);
                    if (methodAnnotation!=null){
//                        allFullPath.add(claszzAnnotation.value()+methodAnnotation.value());
                        controllerMapperList.add(new ControllerMapper(clazz,declaredMethod));
                    }
                }
            }
        }

//        for (ControllerMapper controllerMapper : controllerMapperList) {
//            controllerMapper.setAllControllerPath(allControllerPath);
//            controllerMapper.setAllFullPath(allFullPath);
//        }
    }

    public static ControllerMapper matchMethod(String path){
        for (ControllerMapper controllerMapper : controllerMapperList) {
            if (controllerMapper.matchFull(path)){
                return controllerMapper;
            }
        }
        return null;
    }
}
