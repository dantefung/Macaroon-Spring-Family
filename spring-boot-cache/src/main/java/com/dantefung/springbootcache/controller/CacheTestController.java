/*
 * Copyright (C), 2015-2020
 * FileName: CacheTestController
 * Author:   DANTE FUNG
 * Date:     2020/11/25 14:27
 * Description: 缓存测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/25 14:27   V1.0.0
 */
package com.dantefung.springbootcache.controller;

import com.dantefung.springbootcache.facade.GenericCacheableFacade;
import com.dantefung.springbootcache.sample.DB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Title: CacheTestController
 * @Description: 缓存测试
 * @author DANTE FUNG
 * @date 2020/11/25 14/27
 * @since JDK1.8
 */
@Slf4j
@RestController
@RequestMapping("/test/cache")
public class CacheTestController {

	@Autowired
	@Qualifier("ehCacheManager")
	private CacheManager cacheManager;

	@GetMapping("/load")
	@Cacheable(value = "users")
	public Object load() {
		return new Date().getSeconds();
	}

	@GetMapping("/program")
	public String program() {
		log.info("cacheManager: {}", cacheManager.toString());
		Collection<String> cacheNames = cacheManager.getCacheNames();
		List<String> cacheNameList = new ArrayList<>(cacheNames);
		StringBuilder sb = new StringBuilder("读取的缓存列表为:");
		for (int i = 0; i < cacheNameList.size(); i++) {
			sb.append("\r\n--" + (i + 1) + " " + cacheNameList.get(i) + "\r\n");
		}
		log.info(sb.toString());

		Cache cache = cacheManager.getCache("users");
		cache.put("zhansan", "张三");

		String value = cache.get("zhansan", String.class);
		log.info("read from cache: {}", value);


		EhCacheCacheManager ehCacheCacheManager = (EhCacheCacheManager) cacheManager;
		System.out.println(ehCacheCacheManager);

		log.info("read from cache: {}", value);

		return "ok!";
	}

	/*
		最经典的缓存+数据库读写的模式，就是 Cache Aside Pattern。
		读的时候，先读缓存，缓存没有的话，就读数据库，然后取出数据后放入缓存，同时返回响应。
		更新的时候，

		-->【先更新数据库，然后再删除缓存。】<--

		先做一个说明，从理论上来说，给缓存设置过期时间，是保证最终一致性的解决方案。
		这种方案下，我们可以对存入缓存的数据设置过期时间，所有的写操作以数据库为准，对缓存操作只是尽最大努力即可。
		也就是说如果数据库写成功，缓存更新失败，那么只要到达过期时间，则后面的读请求自然会从数据库中读取新值然后回填缓存。


		(1)先更新数据库，再更新缓存
		这套方案，大家是普遍反对的。为什么呢？有如下两点原因。
		原因一（线程安全角度）
		同时有请求A和请求B进行更新操作，那么会出现
		（1）线程A更新了数据库
		（2）线程B更新了数据库
		（3）线程B更新了缓存
		（4）线程A更新了缓存
		这就出现请求A更新缓存应该比请求B更新缓存早才对，但是因为网络等原因，B却比A更早更新了缓存。这就导致了脏数据，因此不考虑。

		原因二（业务场景角度）
		有如下两点：
		（1）如果你是一个写数据库场景比较多，而读数据场景比较少的业务需求，采用这种方案就会导致，数据压根还没读到，缓存就被频繁的更新，浪费性能。
		（2）如果你写入数据库的值，并不是直接写入缓存的，而是要经过一系列复杂的计算再写入缓存。那么，每次写入数据库后，都再次计算写入缓存的值，无疑是浪费性能的。显然，删除缓存更为适合。


		(2)先删缓存，再更新数据库
		该方案会导致不一致的原因是。同时有一个请求A进行更新操作，另一个请求B进行查询操作。那么会出现如下情形:
		（1）请求A进行写操作，删除缓存
		（2）请求B查询发现缓存不存在
		（3）请求B去数据库查询得到旧值
		（4）请求B将旧值写入缓存
		（5）请求A将新值写入数据库
		上述情况就会导致不一致的情形出现。而且，如果不采用给缓存设置过期时间策略，该数据永远都是脏数据。
		那么，如何解决呢？采用延时双删策略
		伪代码如下

		public void write(String key,Object data){
				redis.delKey(key);
				db.updateData(data);
				Thread.sleep(1000);
				redis.delKey(key);
			}
		转化为中文描述就是
		（1）先淘汰缓存
		（2）再写数据库（这两步和原来一样）
		（3）休眠1秒，再次淘汰缓存
		这么做，可以将1秒内所造成的缓存脏数据，再次删除。
		那么，这个1秒怎么确定的，具体该休眠多久呢？
		针对上面的情形，读者应该自行评估自己的项目的读数据业务逻辑的耗时。然后写数据的休眠时间则在读数据业务逻辑的耗时基础上，加几百ms即可。这么做的目的，就是确保读请求结束，写请求可以删除读请求造成的缓存脏数据。
		如果你用了mysql的读写分离架构怎么办？
		ok，在这种情况下，造成数据不一致的原因如下，还是两个请求，一个请求A进行更新操作，另一个请求B进行查询操作。
		（1）请求A进行写操作，删除缓存
		（2）请求A将数据写入数据库了，
		（3）请求B查询缓存发现，缓存没有值
		（4）请求B去从库查询，这时，还没有完成主从同步，因此查询到的是旧值
		（5）请求B将旧值写入缓存
		（6）数据库完成主从同步，从库变为新值
		上述情形，就是数据不一致的原因。还是使用双删延时策略。只是，睡眠时间修改为在主从同步的延时时间基础上，加几百ms。
		采用这种同步淘汰策略，吞吐量降低怎么办？
		ok，那就将第二次删除作为异步的。自己起一个线程，异步删除。这样，写的请求就不用沉睡一段时间后了，再返回。这么做，加大吞吐量。
		第二次删除,如果删除失败怎么办？
		这是个非常好的问题，因为第二次删除失败，就会出现如下情形。还是有两个请求，一个请求A进行更新操作，另一个请求B进行查询操作，为了方便，假设是单库：
		（1）请求A进行写操作，删除缓存
		（2）请求B查询发现缓存不存在
		（3）请求B去数据库查询得到旧值
		（4）请求B将旧值写入缓存
		（5）请求A将新值写入数据库
		（6）请求A试图去删除请求B写入对缓存值，结果失败了。
		ok,这也就是说。如果第二次删除缓存失败，会再次出现缓存和数据库不一致的问题。
		如何解决呢？
		具体解决方案，且看博主对第(3)种更新策略的解析。

		(3)先更新数据库，再删缓存
		首先，先说一下。老外提出了一个缓存更新套路，名为《Cache-Aside pattern》。其中就指出

		失效：应用程序先从cache取数据，没有得到，则从数据库中取数据，成功后，放到缓存中。
		命中：应用程序从cache中取数据，取到后返回。
		更新：先把数据存到数据库中，成功后，再让缓存失效。
		另外，知名社交网站facebook也在论文《Scaling Memcache at Facebook》中提出，他们用的也是先更新数据库，再删缓存的策略。
		这种情况不存在并发问题么？
		不是的。假设这会有两个请求，一个请求A做查询操作，一个请求B做更新操作，那么会有如下情形产生
		（1）缓存刚好失效
		（2）请求A查询数据库，得一个旧值
		（3）请求B将新值写入数据库
		（4）请求B删除缓存
		（5）请求A将查到的旧值写入缓存
		ok，如果发生上述情况，确实是会发生脏数据。
		然而，发生这种情况的概率又有多少呢？
		发生上述情况有一个先天性条件，就是步骤（3）的写数据库操作比步骤（2）的读数据库操作耗时更短，才有可能使得步骤（4）先于步骤（5）。可是，大家想想，数据库的读操作的速度远快于写操作的（不然做读写分离干嘛，做读写分离的意义就是因为读操作比较快，耗资源少），因此步骤（3）耗时比步骤（2）更短，这一情形很难出现。
		假设，有人非要抬杠，有强迫症，一定要解决怎么办？
		如何解决上述并发问题？
		首先，给缓存设有效时间是一种方案。其次，采用策略（2）里给出的异步延时删除策略，保证读请求完成以后，再进行删除操作。
		还有其他造成不一致的原因么？
		有的，这也是缓存更新策略（2）和缓存更新策略（3）都存在的一个问题，如果删缓存失败了怎么办，那不是会有不一致的情况出现么。比如一个写数据请求，然后写入数据库了，删缓存失败了，这会就出现不一致的情况了。这也是缓存更新策略（2）里留下的最后一个疑问。
		如何解决？
		提供一个保障的重试机制即可，这里给出两套方案。

	 */

	@Autowired
	private GenericCacheableFacade cacheableFacade;
	@Autowired
	private DB db;


	/////////////////////////////Cache Aside Pattern////////////////////////////////
	/**
	 * @Description: 通过key查询value
	 * @param key: 健值
	 * @Author: DANTE FUNG
	 * @Date: 2021/1/9 12:57
	 * @since JDK 1.8
	 * @return: java.lang.String
	 */
	@PostMapping("/query")
	public String queryCache(String key) {
		cacheManager.getCache("users");
		return cacheableFacade.getByKey(key, String.class);
	}

	/**
	 * @Description: 更新数据库的值，并删除缓存
	 * @param key: 键
	 * @param value: 值
	 * @Author: DANTE FUNG
	 * @Date: 2021/1/9 12:58
	 * @since JDK 1.8
	 * @return: java.lang.String
	 */
	@PostMapping("/update")
	public String update(String key, String value) {
		// 更新数据库
		db.put(key, value);
		// 删除缓存
		cacheableFacade.evict(key);
		return "ok!";
	}

	/////////////////////////////Cache Aside Pattern////////////////////////////////


	/*
		Read/Write Through Pattern（读写穿透）
		Read/Write Through Pattern 中服务端把 cache 视为主要数据存储，从中读取数据并将数据写入其中。
		cache 服务负责将此数据读取和写入 DB，从而减轻了应用程序的职责。

		这种缓存读写策略小伙伴们应该也发现了在平时在开发过程中非常少见。抛去性能方面的影响，
		--->【大概率是因为我们经常使用的分布式缓存 Redis 并没有提供 cache 将数据写入 DB 的功能。】<--
	 */

	/*
	Write Behind Pattern（异步缓存写入）
		Write Behind Pattern 和 Read/Write Through Pattern 很相似，-->【两者都是由 cache 服务来负责 cache 和 DB 的读写。】<--
		但是，两个又有很大的不同：Read/Write Through 是同步更新 cache 和 DB，而 Write Behind Caching 则是只更新缓存，不直接更新 DB，
		而是改为异步批量的方式来更新 DB。
		很明显，这种方式对数据一致性带来了更大的挑战，比如 cache 数据可能还没异步更新 DB 的话，cache 服务可能就挂掉了。
		这种策略在我们平时开发过程中也非常少见，但是不代表它的应用场景少，
		比如消息队列中消息的异步写入磁盘、MySQL 的 InnoDB Buffer Pool 机制都用到了这种策略。
		Write Behind Pattern 下 DB 的写性能非常高，非常适合一些数据经常变化又对数据一致性要求没那么高的场景，比如浏览量、点赞量。
	 */
}
