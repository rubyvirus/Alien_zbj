package com.whatistest.util.common;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by rubyvirusqq@gmail.com on 13/06/17.
 * jira:
 * rap:
 * description: 模板文件生成目标类
 */
public class FreeMarkerUtil {


    /**
     * @param templatePath
     * @param templateName
     * @param testPath
     * @param testPathOther
     * @param testClassName
     * @param testMethodName
     */
    public static void gen(String templatePath, String templateName, String testPath, String testPathOther, String testClassName, Set testMethodName) {
        Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        try {
            // 设置模板配置
            configuration.setDirectoryForTemplateLoading(new File(templatePath));
            configuration.setDefaultEncoding("UTF-8");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            // 获取模板
            Template template = configuration.getTemplate(templateName);
            if (Files.notExists(Paths.get(testPath))) {
                Files.createDirectories(Paths.get(testPath));
            }
            if (Files.notExists(Paths.get(testPath + "/" + testClassName + ".java"))) {
                // 添加写入的内容
                Map<String, Object> stringObjectMap = new LinkedHashMap<>();
                stringObjectMap.put("requestClass", testClassName);
                stringObjectMap.put("requestMethod", testMethodName);
                stringObjectMap.put("testPath", testPathOther);

                try {
                    template.process(stringObjectMap, Files.newBufferedWriter(Paths.get(testPath + "/" + testClassName + ".java")));
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
