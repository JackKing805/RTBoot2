package com.rtboot.core.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<File> listFiles(File file){
        List<File> fileList = new ArrayList<>();
        listFiles(file,fileList);
        return fileList;
    }

    private static void listFiles(File file,List<File> fileList){
        if(!file.exists()){
            return;
        }
        if (file.isDirectory()){
            File[] files = file.listFiles();
            if(files==null && files.length==0){
                return;
            }
            for (File file1 : files) {
                listFiles(file1,fileList);
            }
        }else {
            fileList.add(file);
        }
    }
}
