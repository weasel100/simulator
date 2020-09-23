package com.ubg.simulator.utils;

import java.io.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ResourceUtils;

/**
 * @author bruce
 */
@Slf4j
public class UIOUtils {
    /**
     * 读数据流转字符串
     */
    public static String readInputStream(InputStream inStream, String encoding) throws Exception {
        return IOUtils.toString(inStream, encoding);
    }

    /**
     * 读类路径文件转字符串
     */
    public static String readFile(String classpathFile, String encoding) {
        String result = null;
        try {
            String path = ResourceUtils.getFile("response/" + classpathFile).getAbsoluteFile().getPath();
            log.info("优先读JAR包同级目录下：" + path);
            result = FileUtils.readFileToString(new File(path), encoding);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        //如果jar包外没有文件
        if (result == null) {
            try {
                log.info("优先读JAR包内资源文件：" + classpathFile);
                ClassLoader classLoader = UIOUtils.class.getClassLoader();
                result = IOUtils.toString(classLoader.getResourceAsStream("response/" + classpathFile), encoding);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return result;
    }
}
