package com.dantefung.core.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
public interface KvOperate {

	void set(String key, Object value);

	void set(String key, Object value, int timeoutSecond);

	void expire(String key, int timeoutSecond);
	
	Object get(String key);

	void del(String key);

	boolean exists(String key);
	
	long inc(String key);
	
	long incBy(String key, long step);

	Set<String> keys(String pattern);
	
	void delKeysByPrefix(String keyPrefix);

	List<Object> multiGet(Collection<String> keys);

	long lpush(String key, Object value);
	
	Object lpop(String key);
	
	List<Object> range(String key, long start, long end);

	boolean hhasKey(String key, Object hashKey);

	Object hget(String key, Object hashKey);

	Set<Object> hkeys(String key);

	Map<Object, Object> hentries(String key);

	void hput(String key, Object hashKey, Object value);

	long hdelete(String key, Object... hashKeys);
}
