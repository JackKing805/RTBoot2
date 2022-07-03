package com.rtboot.core.utils;

import java.net.URL;

public class PathUtils {
    public static String getProjectPath(){
        String url = getUrl("").getPath();
        if (url.startsWith("/")) {
            url = url.replaceFirst("/", "");
        }
        url = url.replaceAll("/", "\\\\");
        return url;
    }

    public static String convertFilePathToPackagePath(String filePath){
        return filePath.replaceAll("\\\\",".");
    }

    public static URL getUrl(String fileName){
        return PathUtils.class.getClassLoader().getResource(fileName);
    }
}
