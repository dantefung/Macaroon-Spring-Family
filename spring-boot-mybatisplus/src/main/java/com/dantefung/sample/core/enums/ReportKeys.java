package com.dantefung.sample.core.enums;

public class ReportKeys {
    /**
     * Sonar:Utility classes, which are collections of static members,
     * are not meant to be instantiated. Even abstract utility classes,
     * which can be extended, should not have public constructors
     * 所以定义一个非公共构造函数。
     */
    private ReportKeys() {
        throw new IllegalStateException("ReportKeys class");
    }
    public static final String RPT_ABC_REVENUE = "datactr:rpt:abc:revenue";
    public static final String RPT_EDF_STATS = "datactr:rpt:edf:stats";
}
