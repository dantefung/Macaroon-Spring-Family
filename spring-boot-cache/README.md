# Getting Started

## Cache & CacheManager

**Cache**的实现类: `AbstractValueAdaptingCache`、`RedisCache extends AbstractValueAdaptingCache`...

**CacheManager**的实现类: `CaffeineCacheManager`、`EhCacheCacheManager`、`JCacheCacheManager` 、`RedisCacheManager`...
AbstractTransactionSupportingCacheManager
```
public abstract class AbstractCacheManager implements CacheManager, InitializingBean {

    // 分成多个缓存容器  
	private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>(16);

	private volatile Set<String> cacheNames = Collections.emptySet();


	// Early cache initialization on startup

	@Override
	public void afterPropertiesSet() {
		initializeCaches();
	}

	/**
	 * Initialize the static configuration of caches.
	 * <p>Triggered on startup through {@link #afterPropertiesSet()};
	 * can also be called to re-initialize at runtime.
	 * @since 4.2.2
	 * @see #loadCaches()
	 */
	public void initializeCaches() {
		Collection<? extends Cache> caches = loadCaches();

		synchronized (this.cacheMap) {
			this.cacheNames = Collections.emptySet();
			this.cacheMap.clear();
			Set<String> cacheNames = new LinkedHashSet<>(caches.size());
			for (Cache cache : caches) {
				String name = cache.getName();
				this.cacheMap.put(name, decorateCache(cache));
				cacheNames.add(name);
			}
			this.cacheNames = Collections.unmodifiableSet(cacheNames);
		}
	}
	 ... ...
}	
```


```
public class EhCacheCacheManager extends AbstractCacheManager {

... ...

	@Override
	protected Collection<Cache> loadCaches() {
		Assert.notNull(this.cacheManager, "A backing EhCache CacheManager is required");
		Status status = this.cacheManager.getStatus();
		Assert.isTrue(Status.STATUS_ALIVE.equals(status),
				"An 'alive' EhCache CacheManager is required - current cache is " + status.toString());

		String[] names = this.cacheManager.getCacheNames();
		Collection<Cache> caches = new LinkedHashSet<Cache>(names.length);
		for (String name : names) {
			caches.add(new EhCacheCache(this.cacheManager.getEhcache(name)));
		}
		return caches;
	}
... ...
}
```

## @Cacheable & @CacheEvict & @CachePut
由CacheAspectSupport处理.

> Base class for caching aspects, such as the {@link CacheInterceptor} or an
> AspectJ aspect.
>
> <p>This enables the underlying Spring caching infrastructure to be used easily
> to implement an aspect for any aspect system.
>
> <p>Subclasses are responsible for calling relevant methods in the correct order.
>
> <p>Uses the <b>Strategy</b> design pattern. A {@link CacheOperationSource} is
> used for determining caching operations, a {@link KeyGenerator} will build the
> cache keys, and a {@link CacheResolver} will resolve the actual cache(s) to use.
>
> <p>Note: A cache aspect is serializable but does not perform any actual caching
> after deserialization. 

CacheResolver: 

``` 
	@Override
	public void afterSingletonsInstantiated() {
		if (getCacheResolver() == null) {
			// Lazily initialize cache resolver via default cache manager...
			Assert.state(this.beanFactory != null, "CacheResolver or BeanFactory must be set on cache aspect");
			try {
			    // 设置CacheManager构造CacheResolver
				setCacheManager(this.beanFactory.getBean(CacheManager.class));
			}
			catch (NoUniqueBeanDefinitionException ex) {
				throw new IllegalStateException("No CacheResolver specified, and no unique bean of type " +
						"CacheManager found. Mark one as primary or declare a specific CacheManager to use.");
			}
			catch (NoSuchBeanDefinitionException ex) {
				throw new IllegalStateException("No CacheResolver specified, and no bean of type CacheManager found. " +
						"Register a CacheManager bean or remove the @EnableCaching annotation from your configuration.");
			}
		}
		this.initialized = true;
	}

```







