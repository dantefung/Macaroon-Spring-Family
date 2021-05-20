package com.dantefung.core.lang;


/**
 * 数字工具类
 */
public class NumericUtils {

    private NumericUtils() {
    }

    /**
     * 判断数字是否为空
     *
     * @param value 值
     * @return 是否为空
     */
    public static boolean isEmpty(Integer value) {
        return null == value || 0 >= value;
    }

    /**
     * 判断数字是否为非空
     *
     * @param value 值
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Integer value) {
        return null != value && value > 0;
    }

    /**
     * 判断数字是否为空
     *
     * @param value 值
     * @return 是否为空
     */
    public static boolean isEmpty(Long value) {
        return null == value || 0 >= value;
    }

    /**
     * 判断数字是否为非空
     *
     * @param value 值
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Long value) {
        return null != value && value > 0;
    }

    /**
     * 判断数字是否为空
     *
     * @param value 值
     * @return 是否为空
     */
    public static boolean isEmpty(Double value) {
        return null == value || 0 >= value;
    }

    /**
     * 判断数字是否为非空
     *
     * @param value 值
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Double value) {
        return null != value && value > 0;
    }


}