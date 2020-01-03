package com.xiangshangkan.framtest.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 七牛图片本地上传工具
 *
 * @author qhn
 * @date 2019/12/29
 */
public class UploadImageUtil {
    private static Logger logger = LoggerFactory.getLogger(UploadImageUtil.class);

    /**
     * 获取上传token
     *
     * @param online
     * @return java.lang.String
     * @author qhn
     * @date 2019/12/29 15:50
     */
    private static TokenBase getUploadToken(boolean online) {
        final String accessKey = "6KZP9QWlGeJvQb6GsTfapF5uKb0Afwb3DZWZCWfv";
        String secretKey = "0wO_Qd-OoR6xSE09C9ewn606RE4CxCVZfdEykNGO";
        String saveKey = "umc/pic/background/${etag}${ext}";
        String domain = "http://7xln4b.com1.z0.glb.clouddn.com/";
        String bucket = "jjstest";
        if (online) {
            domain = "http://coaimg.leyoujia.com/";
            bucket = "coaimg";
        }

        return new TokenBase().setAccessKey(accessKey).setSecretKey(secretKey)
                .setSaveKey(saveKey).setDomain(domain).setBucket(bucket);
    }


    public static void main(String[] args) {
        String filePath = "E:\\工作资料记录\\2019年12月7日资产线优化版本\\给星瑶的营业执照扫描件副本";
        // 获取图片列表
        List<File> files = getImageFiles(filePath);
        if (CollectionUtils.isEmpty(files)) {
            return;
        }
        // 上传图片
        Map<String, String> fileUrlMap = new LinkedHashMap<>(1024);
        files.stream().forEach(file -> {
            String url = doUpload(file);
            fileUrlMap.put(file.getName(), url);
        });

        // 导出图片
        processBussiness(fileUrlMap, filePath);
    }


    private static void processBussiness(Map<String, String> fileUrlMap, String filePath) {
        if (fileUrlMap == null || fileUrlMap.size() < 1) {
            return;
        }
        Map<String, List<String>> nameUrlListMap = new HashMap<>(1024);
        fileUrlMap.forEach((key, value) -> {
            String realName = key.substring(0, key.lastIndexOf("_"));
            if (nameUrlListMap.containsKey(realName)) {
                nameUrlListMap.get(realName).add(value);
            } else {
                nameUrlListMap.put(realName, Arrays.asList(value));
            }
        });
        //创建内容
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("上传图片名称路径");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        //声明列对象
        HSSFCell cell = null;
        int index = 1;
        for (Map.Entry<String, String> entry : fileUrlMap.entrySet()) {
            String realName = entry.getKey().substring(0, entry.getKey().lastIndexOf("_"));
            row = sheet.createRow(index);
            row.createCell(0).setCellValue(realName);
            row.createCell(1).setCellValue(entry.getKey());
            row.createCell(2).setCellValue(entry.getValue() == null ? "" : entry.getValue());
            index++;
        }

        //导出
        try {
            try {
                File file = new File(filePath);
                if (!file.exists()) {
                    logger.error("文件路径不存在");
                    return;
                }
                if (file.isFile()) {
                    filePath = file.getParentFile().getPath();
                } else {
                    filePath = file.getPath();
                }
                filePath = filePath + "/image_name_url.xls";
            } catch (Exception e) {
                logger.error("文件路径错误", e);
            }
            OutputStream outputStream = new FileOutputStream(filePath);
            wb.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return;

    }


    /**
     * 上传图片到七牛
     *
     * @param file
     * @return java.lang.String
     * @author qhn
     * @date 2019/12/29 15:49
     */
    private static String doUpload(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        String fileName = file.getName();
        //七牛文件
        TokenBase tokenBase = getUploadToken(true);
        String uploadToken = Auth.create(tokenBase.getAccessKey(), tokenBase.getSecretKey())
                .uploadToken(tokenBase.getBucket(), null, 3600, new StringMap().put("saveKey", tokenBase.getSaveKey()));
        UploadManager uploadManager = new UploadManager();
        // 指定文件名（不指定也可以上传，但是没拿到返回url，这里指定url）
        String saveFilename = "umc/pic/background/" + UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
        Response response = null;
        try {
            response = uploadManager.put(file, saveFilename, uploadToken);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        if (!response.isOK()) {
            logger.error("上传失败 fileName:{}, response:{}", fileName, response);
            return null;
        }
        String imageUrl = new StringBuilder(tokenBase.getDomain()).append(saveFilename).toString();
        System.out.println(new StringBuilder(fileName).append(" ").append(imageUrl));
        return imageUrl;
    }

    /**
     * 获取 jpg 和 png 的图片文件列表
     *
     * @param filePath
     * @return List<File>
     * @author qhn
     * @date 2019/12/29 15:36
     */
    private static List<File> getImageFiles(String filePath) {
        List<File> files = getFiles(filePath);
        if (CollectionUtils.isEmpty(files)) {
            return files;
        }
        files = files.stream().filter(file -> {
            String fileName = file.getName().toLowerCase();
            return (fileName.endsWith(".png") || fileName.endsWith("jpg"));
        }).collect(Collectors.toList());
        files.sort(Comparator.comparing(File::getName));

        return files;
    }

    /**
     * 获取文件列表
     *
     * @param filePath
     * @return List<File>
     * @author qhn
     * @date 2019/12/29 15:36
     */
    private static List<File> getFiles(String filePath) {
        if (StringUtils.isNullOrEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            logger.error("上传图片路径不存在 filePath:{}", filePath);
            return null;
        }
        if (file.isFile()) {
            return Arrays.asList(file);
        }
        // 如果是文件夹读取该文件夹下所有的文件，文件夹或者子文件不读
        File[] files = file.listFiles();
        if (files == null || files.length < 1) {
            logger.error("上传图片路径文件夹不存在文件 filePath:{}", filePath);
            return null;
        }
        return Arrays.asList(files);
    }

    private static class TokenBase {
        String accessKey = "6KZP9QWlGeJvQb6GsTfapF5uKb0Afwb3DZWZCWfv";
        String secretKey = "0wO_Qd-OoR6xSE09C9ewn606RE4CxCVZfdEykNGO";
        String saveKey = "umc/pic/background/${etag}${ext}";
        String domain = "http://7xln4b.com1.z0.glb.clouddn.com/";
        String bucket = "jjstest";

        public TokenBase() {
        }

        public TokenBase(String secretKey, String saveKey, String domain, String bucket) {
            this.secretKey = secretKey;
            this.saveKey = saveKey;
            this.domain = domain;
            this.bucket = bucket;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public TokenBase setAccessKey(String accessKey) {
            this.accessKey = accessKey;
            return this;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public TokenBase setSecretKey(String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        public String getSaveKey() {
            return saveKey;
        }

        public TokenBase setSaveKey(String saveKey) {
            this.saveKey = saveKey;
            return this;
        }

        public String getDomain() {
            return domain;
        }

        public TokenBase setDomain(String domain) {
            this.domain = domain;
            return this;
        }

        public String getBucket() {
            return bucket;
        }

        public TokenBase setBucket(String bucket) {
            this.bucket = bucket;
            return this;
        }
    }

}
