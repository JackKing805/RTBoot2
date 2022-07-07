package com.rtboot.boot.http.handler.impl.request;

import com.rtboot.boot.http.factory.ControllerFactory;
import com.rtboot.boot.http.factory.model.ControllerMapper;
import com.rtboot.boot.http.handler.i.RequestHandler;
import com.rtboot.boot.http.handler.model.RequestResult;
import com.rtboot.boot.http.handler.model.ResponseMessage;
import com.rtboot.boot.http.model.RtRequest;
import com.rtboot.boot.http.model.RtResponse;
import com.rtboot.boot.rtboot.core.RtContext;
import com.rtboot.boot.rtboot.factory.RtInjectFactory;
import com.rtboot.boot.rtboot.utils.Logger;
import com.rtboot.boot.rtboot.utils.ReflectUtils;

import java.lang.reflect.InvocationTargetException;

public class ControllerRequestHandler extends RequestHandler {
    @Override
    protected RequestResult handlerRequest(RtContext rtContext, RtRequest rtRequest, RtResponse rtResponse) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        ControllerMapper controllerMapper = ControllerFactory.matchMethod(rtRequest.getRequestUrl().getUrl().getPath());
        if (controllerMapper==null){
            return RequestResult.next();
        }
        Logger.i("controller request:"+ rtRequest);

        Object controller = controllerMapper.getController().newInstance();

        RtInjectFactory.injectObject(controller, clazz -> {
            if (clazz == RtRequest.class){
                return rtRequest;
            }else if(clazz == RtResponse.class){
                return rtResponse;
            }
            return null;
        });

        Object methodResponse = ReflectUtils.invokeMethod(controller, controllerMapper.getMethod(), rtContext.findMethodParameters(controllerMapper.getMethod(), parameter -> {
            Class<?> type = parameter.getType();
            if (type == RtRequest.class) {
                return rtRequest;
            }else if(type == RtResponse.class){
                return rtResponse;
            }
            return null;
        }));

        return RequestResult.success(new ResponseMessage(200,"ok",methodResponse));
    }

//    private Object matchController(RtContext rtContext,String path) throws InstantiationException, IllegalAccessException {
//        List<Class<?>> classes = rtContext.listClassesByAnnotation(Controller.class);
//        for (Class<?> clazz : classes) {
//            Controller annotation = ReflectUtils.getAnnotation(clazz, Controller.class);
//            if (annotation!=null){
//                String value = annotation.value();
//                if (!value.isEmpty() && (path.equals(value) || path.startsWith(value))){
//                    return clazz.newInstance();
//                }
//            }
//        }
//        return null;
//    }
//
//    private List<Method> getControllerMethod(Class<?> clazz){
//        List<Method> methodList = new ArrayList<>();
//        Method[] declaredMethods = clazz.getDeclaredMethods();
//        for (Method declaredMethod : declaredMethods) {
//            if (ReflectUtils.haveAnnotation(declaredMethod,Controller.class)){
//                methodList.add(declaredMethod);
//            }
//        }
//        return methodList;
//    }
//
//    private Method matchControllerMethod(List<Method> methodList,String path){
//        for (Method method : methodList) {
//            Controller annotation = ReflectUtils.getAnnotation(method, Controller.class);
//            if (annotation!=null){
//                String value = annotation.value();
//                if (path.endsWith(value)){
//                    return method;
//                }
//            }
//        }
//        return null;
//    }
}
