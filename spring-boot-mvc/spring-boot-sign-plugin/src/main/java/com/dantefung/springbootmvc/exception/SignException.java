package com.dantefung.springbootmvc.exception;


public class SignException extends BizException {

    /**
     *
     */
    private static final long serialVersionUID = -1391663772116002747L;

    public SignException(String message) {
        super(1000000, message);
    }
}

