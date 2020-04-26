package com.zang.rocket.config;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author Zhang Qiang
 * @date 2019/11/1 17:43
 */
@Slf4j
public class PropertiesConfig {
    public static Properties properties;
    private static final String PROPERTIES_PATH = System.getProperties().get("user.dir") + "\\producer\\src\\main\\resources\\config.properties";
    public static final String producerGroup = get("producerGroup");
    public static final String namervAddr = get("namervAddr");
    public static final String topic = get("topic");

    public PropertiesConfig(String path) {
        properties = getProperties(path);
    }

    public static Properties getProperties(String path) {
        if (properties == null){
            properties = new Properties();
            try {
                properties.load(new FileInputStream(path));
            } catch (IOException e) {
                log.error("读取文件异常, {}" , e.getMessage());
            }
        }
        return properties;
    }

    public static String get(String key) {
        if (properties == null){
            properties = getProperties(PROPERTIES_PATH);
        }
        return properties.getProperty(key);
    }

    public static String get(String path, String key) {
        Properties properties = properties = getProperties(path);
        return properties.getProperty(key);
    }

    public static void forEach(){
        Enumeration fileName = properties.propertyNames();
        while (fileName.hasMoreElements()) {
            String key = (String) fileName.nextElement();
            String v = properties.getProperty(key);
            System.out.println(key + ":" + v);
        }
    }

}
