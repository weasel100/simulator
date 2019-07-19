package com.ubg.simulator.utils;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

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
        String result = "";
        ClassLoader classLoader = UIOUtils.class.getClassLoader();
        try {
            log.info(classpathFile);
            result = IOUtils.toString(classLoader.getResourceAsStream("response/" + classpathFile), encoding);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }
}
