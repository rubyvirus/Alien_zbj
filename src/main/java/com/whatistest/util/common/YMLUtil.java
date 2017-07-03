package com.whatistest.util.common;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

/**
 * Created by rubyvirusqq@gmail.com on 16/05/17.
 * <p>
 * yml公共类，解析
 * <p>
 * 本类为单例模式
 */
public class YMLUtil implements Serializable {


    private static String dataFile;

    private Iterable<Object> tempIterable = null;


    private static class YmlUtil {
        private final static YMLUtil ymlUtil = new YMLUtil();
    }


    public static YMLUtil getInstance(String dataFile) {
        YMLUtil.dataFile = dataFile;
        return YmlUtil.ymlUtil;
    }

    /**
     * 返回case配置linkedHashMap信息
     *
     * @return 测试数据
     */
    public LinkedHashMap getZBJData() {
        return getAll();
    }


    private LinkedHashMap getAll() {
        tempIterable = loadAllYML();
        LinkedHashMap configMap = null;
        // 目前不支持多个对象
        for (Object o : tempIterable) {
            configMap = (LinkedHashMap) o;
            break;
        }
        return configMap;
    }

    /**
     * 初始化Yml， 返回所有数据对象
     *
     * @return Iterable<Object>
     */
    private Iterable<Object> loadAllYML() {
        Yaml yaml = new Yaml();
        Iterable<Object> objectIterable = null;
        try {
            objectIterable = yaml.loadAll(Files.newBufferedReader(Paths.get(dataFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objectIterable;
    }


    /**
     * 加载单个测试数据
     *
     * @return
     */
    public LinkedHashMap loadYML() {
        Yaml yaml = new Yaml();
        LinkedHashMap object = null;
        try {
            object = (LinkedHashMap) yaml.load(Files.newBufferedReader(Paths.get(dataFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }


}
