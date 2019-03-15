package com.xiangshangkan.framtest.web.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author : zhouhui
 * @date : 2019/2/22 14:37
 */
public class ImportExcelUtil {

    public final static String EXCEL_XLS = ".xls";
    public final static String EXCEL_XLSX = ".xlsx";


    public static void importExcel(String address,String path){
        if (address == null || "".equals(address.trim())){
            return;
        }
        try {
            InputStream inputStream = new FileInputStream(new File(address));
            Workbook workbook = null;
            if(address.endsWith(EXCEL_XLS)){
                workbook = new HSSFWorkbook(inputStream);
            } else if (address.endsWith(EXCEL_XLSX)){
                workbook = new XSSFWorkbook(inputStream);
            }
            Sheet sheet = workbook.getSheetAt(0);
            OutputStream outputStream = new FileOutputStream(new File("result"+address.substring(address.lastIndexOf('.'))));
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            System.out.println("未找到对应文件！");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("表格读写错误！");
            e.printStackTrace();
        }
    }

    public static void exportExcel(String title, String[] headers, String[] fields, List<T> datas){
        //创建新的工作bu
        HSSFWorkbook wk = new HSSFWorkbook();
        //创建新的sheet页
        HSSFSheet sheet = wk.createSheet(title);
        //标题行
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
        //合并单元格: CellRangeAddress 的参数分别为起始行，截止行，起始列，截止列
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,fields.length-1));

        //列头
        HSSFRow titleTowRow = sheet.createRow(1);
        for(int i = 0;i < headers.length; i++) {
            HSSFCell cell = titleTowRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        //数据
        for(int i = 0; i < datas.size(); i++ ){
            HSSFRow dataRow = sheet.createRow(i+2);
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(datas.get(i));
            for (int j = 0; j < fields.length; j++ ){
                HSSFCell dataCell = dataRow.createCell(j);
                String value = jsonObject.getString(fields[j]);
                dataCell.setCellValue(value);
            }
        }
        //导出
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sFileName = title + "_"+ sdf.format(new Date())+".xls";
        try {
            OutputStream outputStream = new FileOutputStream(sFileName);
            wk.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       /* try {
            String sFileName = title + "_"+ sdf.format(new Date())+".xls";
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            wb.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

       /* try {
            OutputStream outputStream = new FileOutputStream();
            wk.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
