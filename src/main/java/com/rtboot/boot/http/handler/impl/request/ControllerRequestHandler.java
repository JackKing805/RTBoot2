package com.rtboot.boot.http.handler.impl.request;

import com.rtboot.boot.http.handler.i.RequestHandler;
import com.rtboot.boot.http.model.Request;
import com.rtboot.boot.rtboot.annotation.Controller;
import com.rtboot.boot.rtboot.core.RtContext;
import com.rtboot.boot.rtboot.factory.RtInjectFactory;
import com.rtboot.boot.rtboot.utils.Logger;
import com.rtboot.boot.rtboot.utils.ReflectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ControllerRequestHandler extends RequestHandler {
    @Override
    protected boolean handlerRequest(RtContext rtContext,Request request) {
        Logger.i("controller request:"+request);
        Object controller = null;
        try {
            controller = matchController(rtContext, request.getRequestUrl().getPath());
        } catch (InstantiationException | IllegalAccessException e) {
            return false;
        }
        if (controller==null){
            return false;
        }

        try {
            RtInjectFactory.injectObject(controller, clazz -> {
                if (clazz == Request.class){
                    return request;
                }
                return null;
            });
        } catch (IllegalAccessException e) {
            return false;
        }


        Class<?> aClass = controller.getClass();
        Method method = matchControllerMethod(getControllerMethod(aClass),request.getRequestUrl().getPath());
        if (method==null){
            return false;
        }
        try {
            ReflectUtils.invokeMethod(controller,method, rtContext.findMethodParameters(method, parameter -> {
                Class<?> type = parameter.getType();
                if (type == Request.class){
                    return request;
                }
                return null;
            }));
        } catch (InvocationTargetException | IllegalAccessException e) {
            return false;
        }
        return false;
    }

    private Object matchController(RtContext rtContext,String path) throws InstantiationException, IllegalAccessException {
        List<Class<?>> classes = rtContext.listClassesByAnnotation(Controller.class);
        for (Class<?> clazz : classes) {
            Controller annotation = ReflectUtils.getAnnotation(clazz, Controller.class);
            if (annotation!=null){
                String value = annotation.value();
                if (!value.isEmpty() && (path.equals(value) || path.startsWith(value))){
                    return clazz.newInstance();
                }
            }
        }
        return null;
    }

    private List<Method> getControllerMethod(Class<?> clazz){
        List<Method> methodList = new ArrayList<>();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (ReflectUtils.haveAnnotation(declaredMethod,Controller.class)){
                methodList.add(declaredMethod);
            }
        }
        return methodList;
    }

    private Method matchControllerMethod(List<Method> methodList,String path){
        for (Method method : methodList) {
            Controller annotation = ReflectUtils.getAnnotation(method, Controller.class);
            if (annotation!=null){
                String value = annotation.value();
                if (path.endsWith(value)){
                    return method;
                }
            }
        }
        return null;
    }
}
