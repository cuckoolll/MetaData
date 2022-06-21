package com.meta.metadataserv.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 文件util .
 */
public class FileUtil {
    /**
     * 读取sql文件 .
     * @param multipartFile
     * @return
     */
    public static String readSqlFile(MultipartFile multipartFile) {
        return readFile(multipartFile);
    }

    /**
     * 读取文件返回String .
     * @param multipartFile
     * @return
     */
    public static String readFile(MultipartFile multipartFile) {
        StringBuilder stringBuilder = null;
        try {
            if (multipartFile!=null) {
                InputStream bb = multipartFile.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(bb);
                BufferedReader reader = new BufferedReader(streamReader);
                String line;
                stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                bb.close();
            } else {
                stringBuilder.append("空的");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder == null ? null : stringBuilder.toString();
    }
}
