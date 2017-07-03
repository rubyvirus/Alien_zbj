package com.whatistest.spring;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by rubyvirusqq@gmail.com on 26/05/17.
 * <p>
 * 在指定目录下，搜索文件是否存在
 */
public class SearchFile implements Serializable {

    @Deprecated
    public static String filePaths;

    @Deprecated
    public static void searchFileExists(File dir, String fileName) {
        File[] list = dir.listFiles();
        for (File file : list) {
            if (file.isDirectory()) {
                searchFileExists(file, fileName);
            } else if (file.getPath().contains(fileName)) {
                filePaths = file.getPath();
            }
        }
    }

    /**
     * 应用java8 新增的walk方法流式过滤
     *
     * @param dataPath
     * @param fileName
     * @return
     */
    public static String searchFileExists(String dataPath, String fileName) {
        String tmpFileName = "";
        try {
            tmpFileName = Files.walk(Paths.get(dataPath), FileVisitOption.values()).filter(path -> path.getFileName().toFile().getPath().equalsIgnoreCase(fileName)).findFirst().get().toFile().getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmpFileName;
    }


}
