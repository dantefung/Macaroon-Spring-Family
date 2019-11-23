package com.dantefung.aop.springaopdemo.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BeanUtils
 * 
 */
@Slf4j
public abstract class BeanUtils {
	private static final String BEANS = "beans";

	private BeanUtils() {
		// default
	}

	/**
	 * bean对象转换成map<br>
	 * 入参类支持com.fasterxml.jackson.annotation包下的注解
	 * 
	 * @param obj
	 *            例如实体类
	 * @return 返回Map对象
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> beanToMap(Object obj) {
		if (obj == null) {
			return null;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.convertValue(obj, Map.class);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> beanToMapDeep(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof Map) {
			TempBean tb = new TempBean();
			tb.setObj(obj);
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> objectAsMap = objectMapper.convertValue(tb, Map.class);
			return (Map<String, Object>) objectAsMap.get("obj");
		} else {
			return beanToMap(obj);
		}
	}

	private static class TempBean {
		private Object obj;

		@SuppressWarnings("unused")
		public Object getObj() {
			return obj;
		}

		public void setObj(Object obj) {
			this.obj = obj;
		}
	}

	/**
	 * bean对象转换成map,忽略指定字段<br>
	 * 入参类支持com.fasterxml.jackson.annotation包下的注解
	 * 
	 * <pre>
	 *	注： 请尽量使用com.ting.common.util.BeanUtils.beanToMap(Object) 并配合JsonIgnore注解排除字段
	 * </pre>
	 * 
	 * @param obj
	 *            实体对象
	 * @param ignoreFields
	 *            忽略掉的字段
	 * @return
	 */
	public static Map<String, Object> beanToMap(Object obj, List<String> ignoreFields) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> objectAsMap = beanToMap(obj);

		if (ignoreFields != null) {
			for (String fieldName : ignoreFields) {
				objectAsMap.remove(fieldName);
			}
		}
		return objectAsMap;
	}

	/**
	 * bean对象转换成map,仅包含指定字段<br>
	 * 入参类支持com.fasterxml.jackson.annotation包下的注解
	 * 
	 * <pre>
	 *  注：请尽量使用com.ting.common.util.BeanUtils.beanToMap(Object) 并配合JsonIgnore注解排除字段
	 * </pre>
	 * 
	 * @param obj
	 *            实体对象
	 * @param inFields
	 *            包含字段
	 * @return
	 */
	public static Map<String, Object> beanToMapInFields(Object obj, List<String> inFields) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> objectAsMap = beanToMap(obj);
		Map<String, Object> result = new HashMap<>();
		if (inFields != null) {
			for (String fieldName : inFields) {
				if (objectAsMap.containsKey(fieldName)) {
					result.put(fieldName, objectAsMap.get(fieldName));
				}
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<Map<String, Object>> beanToMapList(List<T> beans) {
		Map<String, Object> tmp = new HashMap<>();
		tmp.put(BEANS, beans);
		tmp = beanToMap(tmp);
		if (tmp.get(BEANS) == null) {
			return new ArrayList<>();
		} else {

			return (List<Map<String, Object>>) tmp.get(BEANS);
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> beanToMapList(Object[] beans) {
		Map<String, Object> tmp = new HashMap<>();
		tmp.put(BEANS, beans);
		tmp = beanToMap(tmp);
		if (tmp.get(BEANS) == null) {
			return new ArrayList<>();
		} else {
			return (List<Map<String, Object>>) tmp.get(BEANS);
		}
	}

	/**
	 * 将map转换成bean
	 * 
	 * @param properties
	 * @param clazz
	 * @return
	 */
	public static <T> T mapToBean(Map<String, ? extends Object> properties, Class<T> clazz) {
		if (properties == null || properties.isEmpty()) {
			return null;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper.convertValue(properties, clazz);
	}

	/***
	 * 将一个Bean或者Map复制到Map对象中
	 * 
	 * @param source
	 * @param target
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void copyToMap(Object source, Map target) {
		Map m = null;
		if (source instanceof Map) {
			m = (Map) source;
		} else {
			m = beanToMap(source);
		}
		target.putAll(m);
	}

	/**
	 * 复制bean属性到另一个bean
	 * 
	 * @param source
	 *            bean或者map
	 * @param target
	 *            只能是bean
	 */
	public static void copyToBean(Object source, Object target) {
		copyProperties(source, target, null, null);
	}

	/**
	 * 实例化知道的Class，并复制bean属性到该实例
	 * 
	 * @param <T>
	 * 
	 * @param source
	 *            bean或者map
	 * @param valueType
	 *            目标Class
	 */
	public static <T> T copyToNewBean(Object source, Class<T> valueType) {
		T target = org.springframework.beans.BeanUtils.instantiateClass(valueType);
		copyProperties(source, target, null, null);
		return target;
	}

	/***
	 * 将beanList转化为另外一个beanList
	 * 
	 * @param sourceList
	 * @param clazz
	 * @return
	 */
	public static <R, S> List<R> toBeanList(List<S> sourceList, Class<R> clazz) {
		List<R> resultList = new ArrayList<>();
		if (sourceList == null) {
			return resultList;
		}
		copyBeanList(sourceList, resultList, clazz);
		return resultList;
	}

	/***
	 * 复制beanList
	 * 
	 * @param sourceList
	 * @param returnList
	 * @param clazz
	 *            返回值类型： 必须有一个无参数的构造函数
	 */
	@SuppressWarnings("unchecked")
	public static <R, S> void copyBeanList(List<S> sourceList, List<R> returnList, Class<R> clazz) {
		if (clazz == null) {
			throw new NullPointerException("传入的返回值类型不能为空");
		}
		if (sourceList == null || returnList == null) {
			return;
		}
		Constructor<?> constructor;
		try {
			constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			for (S s : sourceList) {
				R target = (R) constructor.newInstance();
				BeanUtils.copyToBean(s, target);
				returnList.add(target);
			}
		} catch (Exception e) {
			throw new BeanException("转化过程异常", e);
		}
	}

	/**
	 * 复制bean属性到另一个bean
	 * 
	 * @param source
	 *            bean或者map
	 * @param target
	 *            只能是bean
	 */
	public static void copyToBeanNotNullPreperties(Object source, Object target) {
		copyNotNullProperties(source, target, null, null);
	}

	/**
	 * 复制bean属性到另一个bean,仅包含指定字段
	 * 
	 * @param source
	 *            bean或者map
	 * @param target
	 *            只能是bean
	 * @param infields
	 */
	public static void copyToBeanInFields(Object source, Object target, List<String> infields) {

		copyProperties(source, target, infields, null);

	}

	/**
	 * 复制bean属性到另一个bean,忽略指定字段
	 * 
	 * @param source
	 *            bean或者map
	 * @param target
	 *            只能是bean
	 * @param ignoreFields
	 */
	public static void copyToBeanIgnoreFields(Object source, Object target, List<String> ignoreFields) {
		copyProperties(source, target, null, ignoreFields);
	}
	
	/**
	 * 判断该对象属性值是否全为空
	 * @param obj
	 * @return 返回ture表示所有属性为null  返回false表示不是所有属性都是null
	 * @throws Exception
	 */
	public static boolean isAllFieldNull(Object obj){
        Class stuCla = obj.getClass();// 得到类对象
        Field[] fs = stuCla.getDeclaredFields();//得到属性集合
        boolean flag = true;
        for (Field f : fs) {//遍历属性
        	int m = f.getModifiers();
        	if(Modifier.PRIVATE==m){
        		f.setAccessible(true); // 设置属性是可以访问的(私有的也可以)
                Object val = null;
    			try {
    				val = f.get(obj);
    			} catch (Exception e) {
    				log.error("属性获取异常");
    			}
    			// 得到此属性的值
                if(val!=null) {//只要有1个属性不为空,那么就不是所有的属性值都为空
                    flag = false;
                    break;
                }
        	}
        }
        return flag;
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void copyProperties(Object source, Object target, List<String> infields,
			List<String> ignoreProperties) {
		if (source == null || target == null) {
			return;
		}
		Object sourceObj = source;
		if (source instanceof Map) {
			Map sourceCopyMap = new HashMap((Map) source);// 复制副本

			Map map = sourceCopyMap;
			if (infields != null) {
				map = new HashMap<>();
				for (String infield : infields) {
					map.put(infield, sourceCopyMap.get(infield));
				}
			} else if (ignoreProperties != null) {
				for (String string : ignoreProperties) {
					map.remove(string);
				}
			}

			sourceObj = mapToBean(map, target.getClass());
		}

		copyProperties0(sourceObj, target, infields, ignoreProperties);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void copyNotNullProperties(Object source, Object target, List<String> infields,
			List<String> ignoreProperties) {
		if (source == null || target == null) {
			return;
		}
		Object sourceObj = source;
		if (source instanceof Map) {
			Map map = (Map) source;

			if (infields != null) {
				map = getInfieldMap((Map) source, infields);
			} else if (ignoreProperties != null) {
				map = getIgnoreFieldMap((Map) source, ignoreProperties);
			}

			sourceObj = mapToBean(map, target.getClass());
		}

		copyProperties0(sourceObj, target, infields, ignoreProperties);
	}
	
	/**
	 * 
	 * @Description 保留sourceMap在infields中的key
	 * @param sourceMap
	 * @param infields
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map getInfieldMap(Map sourceMap, List<String> infields) {
		Map map = new HashMap<>();
		for (String infield : infields) {
			if (sourceMap.get(infield) != null) {
				map.put(infield, sourceMap.get(infield));
			}
		}
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map getIgnoreFieldMap(Map sourceMap, List<String> ignoreProperties) {
		Map sourceCopyMap = new HashMap((Map) sourceMap);// 复制副本
		for (String string : ignoreProperties) {
			sourceCopyMap.remove(string);
		}
		return sourceCopyMap;
	}
	/**
	 * 
	 * @param source
	 * @param target
	 * @param infields
	 * @param ignoreProperties
	 * 
	 * @see org.springframework.beans.BeanUtils.copyProperties(Object, Object,
	 *      Class<?>, String...)
	 */
	private static void copyProperties0(Object source, Object target, List<String> infields,
			List<String> ignoreProperties) {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();

		PropertyDescriptor[] targetPds = org.springframework.beans.BeanUtils.getPropertyDescriptors(actualEditable);

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null && (infields == null || infields.contains(targetPd.getName()))
					&& (ignoreProperties == null || (!ignoreProperties.contains(targetPd.getName())))) {

				writeField(source, target, targetPd);

			}
		}
	}

	private static void writeField(Object source, Object target, PropertyDescriptor targetPd) {
		PropertyDescriptor sourcePd = org.springframework.beans.BeanUtils
				.getPropertyDescriptor(source.getClass(), targetPd.getName());
		if (sourcePd != null && sourcePd.getReadMethod() != null) {
			try {
				Method readMethod = sourcePd.getReadMethod();
				if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
					readMethod.setAccessible(true);
				}
				Object value = readMethod.invoke(source);
				Method writeMethod = targetPd.getWriteMethod();
				if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
					writeMethod.setAccessible(true);
				}
				writeMethod.invoke(target, value);
			} catch (Exception ex) {
				log.error("Could not copy properties from source to target", ex);
				throw new FatalBeanException("Could not copy properties from source to target", ex);
			}
		}
	}

	private static class BeanException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public BeanException(String string, Throwable e) {
			super(string, e);
		}

	}
}
