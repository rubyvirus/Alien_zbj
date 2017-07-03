package com.whatistest.generate;

import com.whatistest.capture.GetViewPage;
import com.whatistest.util.common.FreeMarkerUtil;
import com.whatistest.util.common.StringUtil;
import com.whatistest.util.common.YMLUtil;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by rubyvirusqq@gmail.com on 12/06/17.
 * jira:
 * rap:
 * description: 生成http接口相关信息
 */
public class GenerateHTTP {

    public static LinkedHashMap temp = YMLUtil.getInstance("src/main/resources/properties/zbj.yml").loadYML();

    public static void loadAll(String ym, String jv) {
        List list = ((List) ((LinkedHashMap) temp.get("generateHttp")).get(ym));
        // 获取最后一个数字
        String lastNumber = list.get(list.size() - 1).toString();
        // YML数据存放路径
        String interfaceDirPath = temp.get("testData").toString() + "/" + ym;
        String interfaceFilePath = interfaceDirPath + "/" + GetViewPage.getInstance().getInterfaceName(lastNumber) + ".yml";
        // 获取接口字段map
        Map<String, Object> fields = new LinkedHashMap<>();
        GetViewPage.getInstance().parseViewPage(lastNumber).stream().forEach(linkedHashMaps -> {
            fields.put(linkedHashMaps.get(0).get("requestMethod").toString(),
                    linkedHashMaps.get(1));
        });


        // 如果目录不存在就创建
        try {
            if (Files.notExists(Paths.get(interfaceDirPath))) {
                Files.createDirectory(Paths.get(interfaceDirPath));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // 如果文件存在就在名称后面加一个copy
        if (Files.exists(Paths.get(interfaceFilePath))) {
            interfaceFilePath = interfaceFilePath + "copy";
        }

        loadYML(interfaceFilePath, fields);
        loadJava(ym, jv, GetViewPage.getInstance().getInterfaceName(lastNumber), fields.keySet());

    }

    /**
     * 生成yml文件
     *
     * @param filePath
     * @param fields
     */
    private static void loadYML(String filePath, Map fields) {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.DOUBLE_QUOTED);
        Yaml yaml = new Yaml(dumperOptions);
        try {
            yaml.dump(fields, Files.newBufferedWriter(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成java文件
     */
    private static void loadJava(String ym, String ja, String requestClass, Set objects) {
        String testPath = temp.get("testClass").toString() + "/" + ja + "/" + ym;
        FreeMarkerUtil.gen("src/main/java/com/whatistest/generate/freemarker", "TestCase.ftl", testPath, ja + "." + ym, requestClass, objects);
    }

}
