package com.xiangshangkan.framtest.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Consumer;

/**
 * @ClassName ImportPOIUtil
 * @Description TODO
 * @Author zhouhui
 * @Date 2020/1/2 20:22
 * @Version 1.0
 */
public class ImportPOIUtil{


    public static void importThing(String filePath, int sheetIndex,Consumer<String> consumer) throws IOException {
            File file = new File(filePath);
            if (file.exists()) {
                InputStream is = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(is);
                Sheet sheet = workbook.getSheetAt(sheetIndex);
                //获取数据总条数，包括表头
                int rowNum =  sheet.getLastRowNum() + 1;
                //获取第二行
                Row sdRow = sheet.getRow(1);
                //获取数据列数
                int colNum = sdRow.getLastCellNum();
                //获取对象属性名
                List<String> names = new ArrayList<>();
                for (int i = 0; i< colNum;i++) {
                    names.add(ImportPOIUtil.getCellValue(sdRow.getCell(i)));
                }

                for (int i = 2; i< rowNum;i++) {
                    Map<String,Object> result =  ImportPOIUtil.getRowValue(sheet.getRow(i),names);
                    //逐条处理Excel 行
                    consumer.accept(JSONObject.toJSONString(result));
                }
            }
    }


    /**
     * 获取Excel 每行的值
     * @param row
     * @param names
     * @return
     */
    public static Map<String,Object>  getRowValue(Row row, List<String> names) {
        Map<String,Object> map = new HashMap<>();
        int size = names.size();
        for (int i = 0; i < size; i++) {
            String name = names.get(i);
            Cell cell = row.getCell(i);
            map.put(name,getCellValue(cell));
        }
        return map;
    }

    /**
     * @description 获取CEll文本值
     * @param
     * @return
     * @author      zhouhui
     * @date        2019/10/11 17:50
     */
    public static String getCellValue(Cell cell) {
        if (cell == null || "".equals(cell.toString().trim())) {
            return "";
        }
        CellType cellType = cell.getCellTypeEnum();
        DecimalFormat dft = new DecimalFormat("#.##");
        switch (cellType) {
            case _NONE:
            case BLANK:
            case ERROR:
                return "";
            case STRING:
                return String.valueOf(cell.getStringCellValue()).trim();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue()).trim();
            case NUMERIC:
            case FORMULA:
                if(DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                } else {
                    return String.valueOf(dft.format(cell.getNumericCellValue())).trim();
                }
            default:
                return "";
        }
    }
}
