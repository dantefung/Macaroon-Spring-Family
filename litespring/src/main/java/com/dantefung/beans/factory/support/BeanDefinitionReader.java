package com.dantefung.beans.factory.support;

public interface BeanDefinitionReader {

    void loadBeanDefinitions(String location) throws Exception;
}
