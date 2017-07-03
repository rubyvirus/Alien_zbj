package com.whatistest.util.common;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by rubyvirusqq@gmail.com on 13/05/17.
 * <p>
 * Excel公共类
 */
public class ExcelUtil implements Serializable {
    // 通过文件生成SQL语句列表
    public static Map<String, Map<String, List<String>>> generateSql(List<Path> allFileName) {
        final Map<String, Map<String, List<String>>>[] tempMap = new Map[]{new HashMap()};

        allFileName.stream().forEach(path -> {
            String fileName = StringUtil.substringBefore(path.getFileName().toString(), ".");
            if (path.getFileName().toString().endsWith("xls")) {
                try {
                    tempMap[0].put(fileName, handleXLS(fileName, Files.newInputStream(path)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (path.getFileName().toString().endsWith("xlsx")) {
                try {
                    tempMap[0].put(fileName, handleXLSX(fileName, Files.newInputStream(path)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return tempMap[0];
    }

    // 处理excel2007以上文件
    private static Map<String, List<String>> handleXLSX(String string, InputStream inputStream) {
        try {
            return commonParseWorkBook(string, new XSSFWorkbook(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 处理excel2003文件
    private static Map<String, List<String>> handleXLS(String string, InputStream inputStream) {
        try {
            return commonParseWorkBook(string, new HSSFWorkbook(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 处理流程
     * １. 获取所有的sheet
     * 2. 解析每个sheet，组装为SQL list
     *
     * @param string   文件名
     * @param workbook
     */
    private static Map<String, List<String>> commonParseWorkBook(String string, Workbook workbook) {
        Map<String, List<String>> resultMap = new HashMap<>();
        StreamSupport.stream(workbook.spliterator(), true).forEach(sheet -> {
            resultMap.put(sheet.getSheetName(), commonParseSheet(string, sheet));
        });

        return resultMap;
    }

    /**
     * sheet名就是数据库表名
     *
     * @param string
     * @param sheet
     */
    private static List<String> commonParseSheet(String string, Sheet sheet) {
        String tableName = sheet.getSheetName();
        final String[] commonSQL = {"INSERT INTO `" + string + "." + tableName + "` (field) VALUES (value);"};
        final String[] fields = {""};
        List<String> resultSQL = new ArrayList<>();
        StreamSupport.stream(sheet.spliterator(), true).forEach(row -> {
            Stream<Cell> cellStream = StreamSupport.stream(row.spliterator(), true);
            // 处理第一行
            if (row.getRowNum() == 0) {
                cellStream.forEach(cell -> fields[0] += "`" + cell.getStringCellValue() + "`,");
                fields[0] = fields[0].substring(0, fields[0].lastIndexOf(","));
                commonSQL[0] = StringUtil.replace(commonSQL[0], "field", fields[0]);
            } else {
                final String[] temp2 = commonSQL;
                final String[] temp = {""};
                cellStream.forEach(cell -> {
                    if (cell.getCellTypeEnum() == CellType.STRING) {
                        temp[0] += ",\'" + cell.getStringCellValue() + "\'";
                    } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
                        temp[0] += "," + cell.getBooleanCellValue();
                    } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                        temp[0] += "," + cell.getNumericCellValue();
                    } else if (cell.getCellTypeEnum() == CellType._NONE) {
                        temp[0] += ",\"\"";
                    }
                });
                resultSQL.add(StringUtil.replace(temp2[0], "value", temp[0].replaceFirst("[,]", "")));
            }
        });
        return resultSQL;
    }
}
