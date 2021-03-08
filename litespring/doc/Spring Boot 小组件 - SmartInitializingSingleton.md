> 本文由 [简悦 SimpRead](http://ksria.com/simpread/) 转码， 原文地址 [www.cnblogs.com](https://www.cnblogs.com/imyijie/p/12702617.html)

前言
--

我们经常会看到或使用 `InitializingBean`（或`@PostConstruct`）进行 Bean 的一个初始化过程，但是有时候会发现 `InitializingBean` 存在一些不太适用的场景。

比如我们有以下一个 `Dog` 类

```
@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Dog {

    public void makeSound() {
        System.out.println("bark!");
    }
}
```

这个 `Dog` 是一个 prototype 的 Bean，每次我们从 `BeanFactory` 中获取这个类时都会创建一个新的类。

然后我们有一个 `Person` 类，初始化的时候会需要一只狗叫一下

```
@Service
public class Person implements InitializingBean, BeanFactoryAware {

    private BeanFactory beanFactory;
    private List<Dog> managedDogs = new LinkedList<>();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Dog dog = beanFactory.getBean(Dog.class);
        dog.makeSound();
    }

    public void addDog(Dog dog) {
        managedDogs.add(dog);
    }
}
```

到这还没啥事，运行一下可以正常地找到 “bark！” 的输出。

这时候突然来了一个针对 `Dog` 的`BeanPostProcessor`，好巧不巧还依赖 `Person`

```
@Component
public class DogPostProcessor implements BeanPostProcessor {

    @Autowired
    private Person person;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!(bean instanceof Dog)) {
            return null;
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Dog.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (method.getName().equals("makeSound")) {
                System.out.println(" 注意了狗狗要开始叫了!");
            }
            return method.invoke(bean, args);
        });
        Dog proxyDog = (Dog) enhancer.create();
        person.addDog(proxyDog);
        return proxyDog;
    }
}
```

在这个 `DogPostProcessor` 里对 `makeSound` 方法做了一个前置处理：预告要狗狗要开始叫了。最后还会把狗狗给到 `Person` 类进行管理。

这时候我们再次运行程序，发现并没有 " 注意了狗狗要开始叫了!" 的输出，同时 `person` 中也没有发现新增的 `Dog` 实例。为什么会这样呢？

我们可以发现，在程序启动过程中，出现了一个信息

```
Bean 'dog' of type [com.example.Dog] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
```

这表示 dog 没有经过所有的 `BeanPostProcessor` 就被创建了。造成这种局面的原因主要是 `DogPostProcessor` 的创建依赖 `Person` 的创建，而在 `Person` 创建的过程中有一个调用初始化方法的子过程，在这个子过程里需要从 `BeanFactory` 获得一个 `Dog` 的实例。这时 `DogPostProcessor` 都没初始化完呢，自然 `Dog` 也无法被处理了。

说来说去，还是这个调用初始化方法的时机不太合适。能不能有一种办法，提供一个在所有 Bean 都创建后才调用的初始化方法呢？没错，`SmartInitializingSingleton` 正是因此而生。

> 本文所使用的源码版本为 2.2.2.RELEASE，如有出入请检查版本是否不一致。

从哪开始
----

我们来到 `org.springframework.context.support.AbstractApplicationContext#refresh` 方法，这是上下文创建时调用的方法，里面会一步一步构建好整个上下文。当所有的前置工作都做好时，会调用到 `finishBeanFactoryInitialization(beanFactory)` 进行所有在前置工作时还没初始化的（也是绝大多数的）Singleton Bean 的初始化工作。

最关键的部分是 `DefaultListableBeanFactory#preInstantiateSingletons` 方法, 精简后如下所示：

```
// 请注意以下代码有大量删改，只留下了关键示意代码，请自行查看源代码。
	@Override
	public void preInstantiateSingletons() throws BeansException {
		List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);

		for (String beanName : beanNames) {
			getBean(beanName);
		}

		for (String beanName : beanNames) {
			Object singletonInstance = getSingleton(beanName);
			if (singletonInstance instanceof SmartInitializingSingleton) {
				final SmartInitializingSingleton smartSingleton = (SmartInitializingSingleton) singletonInstance;
				smartSingleton.afterSingletonsInstantiated();
			}
		}
	}
```

可以看到在所有 Bean 都初始完后，遍历判断了每个 Singleton Bean 是否实现了 `SmartInitializingSingleton` 接口，然后对实现此接口的实例调用 `afterSingletonsInstantiated` 方法。自然这个初始化方法被调用时，所有的 Bean 都创建好了。也就是说这个接口将初始化方法的调用和 Bean 的创建过程分开了。

> 其实在前言中提到的这个场景，你可能会想到另一种办法处理，那就是通过实现 `ApplicationListener<ContextRefreshedEvent>`接口。上下文都刷新完成后，自然会通知到这个接口，当然这样稍显复杂，而且看起来也不太像一个 Bean 的初始化了。

重新出发
----

我们把 `Person` 改改

```
@Service
public class Person implements SmartInitializingSingleton, BeanFactoryAware {

    private BeanFactory beanFactory;
    private List<Dog> managedDog = new LinkedList<>();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void addDog(Dog dog) {
        managedDog.add(dog);
    }

    @Override
    public void afterSingletonsInstantiated() {
        Dog dog = beanFactory.getBean(Dog.class);
        dog.makeSound();
    }
}
```

然后再运行一下

```
注意了狗狗要开始叫了!
bark!
```

嗯，一切都正常了。