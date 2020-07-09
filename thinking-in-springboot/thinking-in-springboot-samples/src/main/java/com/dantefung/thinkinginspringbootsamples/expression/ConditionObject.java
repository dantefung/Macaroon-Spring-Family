package com.dantefung.thinkinginspringbootsamples.expression;

public interface ConditionObject<T> {

    T  getObject();

    Class<?> getObjectType();


}
