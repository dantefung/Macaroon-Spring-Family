package com.dantefung.beans.factory;

/**
 * @Description: 生产bean的工厂，它负责生产和管理各个bean的实例。
 * @Author: DANTE FUNG
 * @Date: 2020/11/11 15:29
 * @since JDK 1.8
 */
public interface BeanFactory {

	Object getBean(String beanID) throws Exception;

}
