package com.dantefung.knife4j.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户对象")
public class UserVO {

    @ApiModelProperty(required = true,notes = "用户名",example = "blues")
    private String name;

    @ApiModelProperty(required = true,notes = "用户返回消息",example = "hello world")
    private String words;


    public UserVO(String name, String words) {
        this.name = name;
        this.words = words;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}