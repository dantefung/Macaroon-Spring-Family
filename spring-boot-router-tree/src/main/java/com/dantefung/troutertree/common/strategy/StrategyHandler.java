package com.dantefung.troutertree.common.strategy;

/**
 * @Description:
 * @return:
 * @Author: DANTE FUNG
 * @Date: 2020/9/16
 */
public interface StrategyHandler<T, R> {

    @SuppressWarnings("rawtypes")
    StrategyHandler DEFAULT = t -> null;

    /**
     * apply strategy
     *
     * @param param
     * @return
     */
    R apply(T param);
}