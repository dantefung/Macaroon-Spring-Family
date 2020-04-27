package com.dantefung.sample.core.exception;

public class DataCtrException extends BizException {

    public DataCtrException(int code, String msg) {
        super(code, msg);
    }

    public static final DataCtrException KEY_EXCEPTION = new DataCtrException(99999990, "报表KEY不存在");
}