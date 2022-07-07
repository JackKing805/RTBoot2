package com.rtboot.boot.http.factory.model;

import com.rtboot.boot.http.annotation.Controller;
import com.rtboot.boot.rtboot.utils.ReflectUtils;

import java.lang.reflect.Method;

public class ControllerMapper {
    private final Class<?> controller;
    private final Method method;

//    private List<String> allFullPath;
//    private List<String> allControllerPath;
//
//    public void setAllControllerPath(List<String> allControllerPath) {
//        this.allControllerPath = new ArrayList<>();
//        this.allControllerPath.addAll(allControllerPath);
//        this.allControllerPath.remove(getParentPath());
//
//        this.allControllerPath.sort((o1, o2) -> o2.length() - o1.length());
//    }
//
//    public void setAllFullPath(List<String> allFullPath) {
//        this.allFullPath = new ArrayList<>();
//        this.allFullPath.addAll(allFullPath);
//        this.allFullPath.remove(getPath());
//        this.allFullPath.sort((o1, o2) -> o2.length() - o1.length());
//    }

    public ControllerMapper(Class<?> controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public Class<?> getController() {
        return controller;
    }

    public Method getMethod() {
        return method;
    }

    public String getPath(){
        Controller annotation1 = ReflectUtils.getAnnotation(method, Controller.class);
        String p2 = annotation1.value();
        return getParentPath() + p2;
    }

    public String getParentPath(){
        Controller annotation = ReflectUtils.getAnnotation(controller, Controller.class);
        return annotation.value();
    }

    public boolean matchFull(String path){
        String full = getPath();
        if (path.equals(full)){
            return true;
        }
        return false;
    }


//    /a/b
//    /a/f
//    /a/f/c
//    /a/css/style.css
}
