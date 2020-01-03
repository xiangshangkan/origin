package com.xiangshangkan.framtest.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * @ClassName ImportPOIUtil
 * @Description TODO
 * @Author zhouhui
 * @Date 2020/1/2 20:22
 * @Version 1.0
 */
public class ImportPOIUtil {

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
