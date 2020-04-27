package com.dantefung.sample.core.enums;


import com.dantefung.sample.core.exception.DataCtrException;

/**
 *  报表枚举
 * @author DANTE FUNG
 * @create 2020-4-27 18:03:23
 * @since 1.0.0
 */
public enum ReportKeyEnums {

    RPT_ABC_STATS("com.dantefung.sample.teacher.mapper.UserMapper.selectUserDailyReport",ReportKeys.RPT_ABC_REVENUE,false),
    RPT_EDF_REVENUE("com.dantefung.sample.teacher.mapper.UserMapper.selectUserMonthReport",ReportKeys.RPT_EDF_STATS,false),
    ;

    /** 描述 */
    private String sqlKey;
    /** 枚举值 */
    private String value;
    /** 是否缓存 */
    private Boolean isCache;

    ReportKeyEnums(String sqlKey, String value,Boolean isCache) {
        this.sqlKey = sqlKey;
        this.value = value;
        this.isCache = isCache;
    }

    public String getValue() {
        return value;
    }

    public String getSqlKey() {
        return sqlKey;
    }

    public Boolean getCache() {
        return isCache;
    }

    /**
     * 根据枚举的value获取对应的枚举sqlKey
     * @param value
     * @return
     */
    public static ReportKeyEnums getEnum(String value) {
        ReportKeyEnums resultEnum = null;
        ReportKeyEnums[] enumAry = ReportKeyEnums.values();
        for (int i = 0; i < enumAry.length; i++) {
            if ( enumAry[i].getValue().equals(value)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        if (null != resultEnum) { return resultEnum; }
        throw DataCtrException.KEY_EXCEPTION;
    }

    /**
     * 根据sqlKey获取枚举
     * @param sqlKey
     * @return
     */
    public static ReportKeyEnums getEnumBySqlKey(String sqlKey) {
        ReportKeyEnums resultEnum = null;
        ReportKeyEnums[] enumAry = ReportKeyEnums.values();
        for (int i = 0; i < enumAry.length; i++) {
            if ( enumAry[i].getSqlKey().equals(sqlKey)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }
}
