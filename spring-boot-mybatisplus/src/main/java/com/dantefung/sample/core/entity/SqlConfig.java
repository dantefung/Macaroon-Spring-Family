package com.dantefung.sample.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@TableName("datactr_sql_config")
public class SqlConfig implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;

    /**sqlKey*/
    private String sqlKey;

    /**功能权限编码*/
    private String functionKey;

    /**数据权限keys，多个key以逗号分隔*/
    private String permissionKeys;

    /**数据权限条件，多个条件以逗号分隔 R.project_code in( {0} )*/
    private String permissionConditions;

    /**参数校验JSON数据*/
    private String checkParam;

    /**sql脚本*/
    private String sqlText;

    /**缓存状态 0不缓存 1 缓存*/
    private Integer cacheStatus;

    /**是否需要授权 0不需要 1 需要*/
    private Integer permissionStatus;

    @Override
    public String toString() {
        return "SqlConfig{" +
                "sqlKey='" + sqlKey + '\'' +
                '}';
    }
}
