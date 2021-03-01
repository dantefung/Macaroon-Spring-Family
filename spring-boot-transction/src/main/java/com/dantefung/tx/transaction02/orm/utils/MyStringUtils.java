package com.dantefung.tx.transaction02.orm.utils;

import com.dantefung.tx.transaction02.orm.annotation.MyColumn;
import com.dantefung.tx.transaction02.orm.annotation.MyId;
import com.dantefung.tx.transaction02.orm.annotation.MyTableName;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

public class MyStringUtils {


	/**
	 *@Description 将第一个字母替换为大写
	 *@Author DANTE FUNG
	 *@Date 2021-03-01 23:31:08
	 *@Param [str]
	 *@return java.lang.String
	 */
	public static String firstUpperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
	}

	/**
	 *@Description 获取属性的set方法
	 *@Author DANTE FUNG
	 *@Date 2021-03-01 23:31:08
	 *@Param [fieldName]
	 *@return java.lang.String
	 */
	public static String getSetMethod(String fieldName) {
		return "set" + firstUpperCase(fieldName);
	}

	/**
	 *@Description 根据实体获取表名称
	 *@Author DANTE FUNG
	 *@Date 2021-03-01 23:31:08
	 *@Param [clazz]
	 *@return java.lang.String
	 */
	public static String getTableName(Class<?> clazz) {
		//默认获取类中的小写名称
		String tableName = clazz.getSimpleName();
		if (null != clazz) {
			//判断类是否加载了自定义注解
			if (clazz.isAnnotationPresent(MyTableName.class)) {
				MyTableName myTableName = clazz.getAnnotation(MyTableName.class);
				tableName = myTableName.tableName();

			}
		}
		return tableName;
	}

	/**
	 *@Description 根据属性名称获取字符名称
	 *@Author DANTE FUNG
	 *@Date 2021-03-01 23:31:44
	 *@Param [field]
	 *@return java.lang.String
	 */
	public static String getTableFieldName(Field field) {
		String fieldName = "";
		if (null != field) {
			if (field.isAnnotationPresent(MyColumn.class)) {
				MyColumn annotation = field.getAnnotation(MyColumn.class);
				if (Objects.nonNull(annotation)) {
					fieldName = annotation.columnName();
					if (StringUtils.isBlank(fieldName)) {
						fieldName = field.getName();
					}
				} else {
					fieldName = field.getName();
				}
			} else if (!field.isAnnotationPresent(MyId.class)) {
				fieldName = field.getName();
			}
		}
		return fieldName;
	}

	/**
	 *@Description 根据字段获取主键的名称
	 *@Author DANTE FUNG
	 *@Date 2019/9/2 17:17
	 *@Param [field]
	 *@return java.lang.String
	 */
	public static String getTableIdName(Field field) {
		String idName = "";
		if (null != field) {
			if (field.isAnnotationPresent(MyId.class)) {
				MyId muyanId = field.getAnnotation(MyId.class);
				if (!Objects.equals("", muyanId)) {
					idName = muyanId.idName();
				}

			} else if (!field.isAnnotationPresent(MyColumn.class)) {
				idName = field.getName();
			}
		}
		return idName;

	}
}