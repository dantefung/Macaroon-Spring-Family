package com.dantefung.core.collection;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 集合工具类
 */
public class CollectionUtils {

    private CollectionUtils() {
    }

    /**
     * List转Map
     *
     * @param list     列表
     * @param idGetter id获取方法
     * @param <T>      键类型
     * @param <V>      值类型
     * @return Map
     */
    public static <T, V> Map<T, V> toMap(List<V> list, Function<V, T> idGetter) {
        if (isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(
                idGetter,
                Function.identity(),
                (t1, t2) -> t2
        ));
    }

    /**
     * List转LinkedMap
     *
     * @param list     列表
     * @param idGetter id获取方法
     * @param <T>      键类型
     * @param <V>      值类型
     * @return LinkedMap
     */
    public static <T, V> Map<T, V> toLinkedMap(List<V> list, Function<V, T> idGetter) {
        if (isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(
                idGetter,
                Function.identity(),
                (t1, t2) -> t2,
                LinkedHashMap::new
        ));
    }

    /**
     * 通过键获取值
     *
     * @param map map
     * @param ids 键列表
     * @param <T> 键类型
     * @param <V> 值类型
     * @return 值列表
     */
    public static <T, V> List<V> toList(Map<T, V> map, List<T> ids) {
        if (isEmpty(map) || isEmpty(ids)) {
            return Collections.emptyList();
        }
        return ids.stream()
                .filter(id -> null != map.get(id))
                .map(map::get)
                .collect(Collectors.toList());
    }

    /**
     * 按照键分组
     *
     * @param list     列表
     * @param idGetter id获取方法
     * @param <T>      键类型
     * @param <V>      值类型
     * @return 分组后Map
     */
    public static <T, V> Map<T, List<V>> grouping(List<V> list, Function<V, T> idGetter) {
        if (isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(
                Collectors.groupingBy(
                        idGetter,
                        Collectors.toList()
                ));
    }

    /**
     * 按照键分组
     *
     * @param list     列表
     * @param idGetter id获取方法
     * @param <T>      键类型
     * @param <V>      值类型
     * @return 分组后LinkedMap
     */
    public static <T, V> Map<T, List<V>> groupingToLinkedMap(List<V> list, Function<V, T> idGetter) {
        if (isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(
                Collectors.groupingBy(
                        idGetter,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

    /**
     * 获取子Map
     *
     * @param map map
     * @param ids 键列表
     * @param <T> 键类型
     * @param <V> 值类型
     * @return 子Map
     */
    public static <T, V> Map<T, V> getSubMap(Map<T, V> map, List<T> ids) {
        return getSubMap(map, ids, HashMap::new);
    }

    /**
     * 获取子LinkedMap
     *
     * @param map map
     * @param ids 键列表
     * @param <T> 键类型
     * @param <V> 值类型
     * @return 子LinkedMap
     */
    public static <T, V> Map<T, V> getSubLinkedMap(Map<T, V> map, List<T> ids) {
        return getSubMap(map, ids, LinkedHashMap::new);
    }

    /**
     * 获取子LinkedMap
     *
     * @param map        map
     * @param ids        键列表
     * @param mapFactory map工厂
     * @param <T>        键类型
     * @param <V>        值类型
     * @param <M>        map类型
     * @return 子LinkedMap
     */
    private static <T, V, M extends Map<T, V>> Map<T, V> getSubMap(Map<T, V> map, List<T> ids
            , Supplier<M> mapFactory) {
        if (isEmpty(map) || isEmpty(ids)) {
            return Collections.emptyMap();
        }
        Map<T, V> result = mapFactory.get();
        ids.forEach(id -> {
            V v = map.get(id);
            if (null != v) {
                result.put(id, v);
            }
        });
        return result;
    }

    /**
     * 判断集合是否为空
     *
     * @param collection 集合
     * @return 判断结果
     */
    public static boolean isEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * 判断集合是否为非空
     *
     * @param collection 集合
     * @return 判断结果
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断map是否为空
     *
     * @param map Map
     * @return 判断结果
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }

    /**
     * 判断map是否为非空
     *
     * @param map Map
     * @return 判断结果
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

}