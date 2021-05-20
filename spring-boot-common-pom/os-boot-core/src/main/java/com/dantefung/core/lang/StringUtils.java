package com.dantefung.core.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 字符串工具类
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

    private StringUtils() {
    }

    /**
     * 字符串转int列表
     *
     * @param source 字符串
     * @param split  分隔符
     * @return int列表
     */
    public static List<Integer> toIntList(String source, String split) {
        source = trimToNull(source);
        if (null == source) {
            return Collections.emptyList();
        }
        String[] ids = split(source, split);
        List<Integer> result = new ArrayList<>();
        for (String id : ids) {
            id = trimToNull(id);
            if (null != id) {
                result.add(Integer.parseInt(id));
            }
        }
        return result;
    }

    /**
     * 字符串转int列表
     *
     * @param source 字符串
     * @return int列表
     */
    public static List<Integer> toIntList(String source) {
        return toIntList(source, ",");
    }
}