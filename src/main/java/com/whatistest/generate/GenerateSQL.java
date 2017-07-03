package com.whatistest.generate;

import com.whatistest.util.common.ExcelUtil;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by rubyvirusqq@gmail.com on 13/06/17.
 * jira:
 * rap:
 * description: 将Excel数据自动转换为SQL脚本，插入前置数据
 * 每执行一次所有数据都要重新生成一遍
 * 传入一个路径， 经过以下检查：
 * 1. 检查是否存在Excel
 * 1. 检查是否存在Sql目录，没有就创建，有就删除所有SQL脚本
 * 2. 遍历解析所有excel文件，解析方式：一个Excel文件表示一个库，一个Tab页表示一个表Sql脚本，一行表示插入一条数据
 */
public class GenerateSQL implements Serializable {

    /**
     * 生成SQL
     * 调用ExcelUtil 返回map-->key=sheetName,value=sql list
     *
     * @param excelPath
     */
    public void generateSQLScript(String excelPath) {

        // 文件存在就生成SQL
        if (checkAndCreateDir(excelPath)) {
            Map<String, Map<String, List<String>>> generateMap = ExcelUtil.generateSql(getAllFileName(excelPath + "/excel"));
            Path sqlPath = Paths.get(excelPath + "/sql");
            // 如果文件存在就创建SQL脚本
            if (Files.exists(sqlPath)) {
                generateMap.forEach((key, value) -> {
                    try {
                        BufferedWriter sqlFile = Files.newBufferedWriter(Paths.get(excelPath + "/sql/" + key + ".sql"));
                        value.forEach((key2, value2) -> {
                            try {
                                sqlFile.newLine();
                                // 备注
                                sqlFile.write("-- " + key2.toString());
                                sqlFile.newLine();
                                // sql文件
                                value2.forEach(value3 -> {
                                    try {
                                        sqlFile.write(value3);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        sqlFile.flush();
                        sqlFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

    }

    // 获取已xls,xlsx结尾的文件名
    private List<Path> getAllFileName(String excelPath) {
        List<Path> temp = null;
        try {
            temp = Files.list(Paths.get(excelPath)).filter(path -> path.getFileName().toString().endsWith("xls") || path.getFileName().toString().endsWith(".xlsx")).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 检查目录是否存在
     * 目录下是否还有Excel文件.xls,.xlsx
     *
     * @param excelPath
     * @return 返回是否成功
     */
    private Boolean checkAndCreateDir(String excelPath) {

        Boolean isOK = Boolean.TRUE;

        Path pathExcel = Paths.get(excelPath + "/excel");
        Path pathSQL = Paths.get(excelPath + "/sql");

        // Excel目录不否存在，就返回false
        try {
            if (Files.notExists(pathExcel) && Files.list(pathExcel).count() <= 0) {
                isOK = Boolean.FALSE;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //　如果SQL目录存在就删除
        try {
            if (Files.exists(pathSQL)) {
                FileUtils.cleanDirectory(pathSQL.toFile());
                Files.delete(pathSQL);
            }
            Files.createDirectory(pathSQL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isOK;
    }

    public static void main(String[] args) {
        new GenerateSQL().generateSQLScript("src/main/resources/testData/openPlatform");
    }


}
