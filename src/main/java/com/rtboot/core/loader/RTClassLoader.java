package com.rtboot.core.loader;

import com.rtboot.core.utils.FileUtils;
import com.rtboot.core.utils.Logger;
import com.rtboot.core.utils.PathUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RTClassLoader {
    public static List<Class<?>> loadClasses(){
        String projectPath = PathUtils.getProjectPath();
        Logger.i("projectPath:"+projectPath);
        List<File> files = FileUtils.listFiles(new File(projectPath));
        return loadClassesByFiles(projectPath,files);
    }

    private static List<Class<?>> loadClassesByFiles(String prefix,List<File> fileList){
        List<Class<?>> classList = new ArrayList<>();
        for (File file : fileList) {
            Logger.i("projectFile:"+file.getAbsolutePath());
            if (file.getName().endsWith(".class")){
                String packageName = PathUtils.convertFilePathToPackagePath(file.getAbsolutePath().replace(prefix,"")).replace(".class","");
                try{
                    Class<?> aClass = Class.forName(packageName);
                    if(!aClass.isInterface()){
                        classList.add(aClass);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return classList;
    }
}
