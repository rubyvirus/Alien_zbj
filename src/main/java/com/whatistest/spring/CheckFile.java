package com.whatistest.spring;

import java.io.File;

/**
 * Created by rubyvirusqq@gmail.com on 26/05/17.
 * 检查文件顺序如下：
 * 1.先检查文件格式，只支持yml,json,excel(xlsx)
 * 2.检查文件是否存在
 */
public class CheckFile {

    public static Boolean checkFiles(String filePath) {
        return checkFileExists(filePath);
    }

    public static Boolean checkDirs(String dirPath) {
        return checkFiles(dirPath) && checkDirExists(dirPath);
    }

    private static boolean checkDirExists(String dirPath) {
        return new File(dirPath).isDirectory();
    }


    private static boolean checkFileExists(String filePath) {
        return new File(filePath).exists();
    }

    public static boolean checkFilesType(String filePath) {
        return filePath.endsWith("yml") || filePath.endsWith("json") || filePath.endsWith("xlsx");
    }
}
