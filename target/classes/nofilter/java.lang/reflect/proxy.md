Proxy#newProxyInstance核心逻辑如下
```
public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          InvocationHandler h){
        final Class<?>[] intfs = interfaces.clone();
        /*
         * Look up or generate the designated proxy class.
         * 查找或者生成代理类
         */
        Class<?> cl = getProxyClass0(loader, intfs);
        /*
         * Invoke its constructor with the designated invocation handler.
         * 使用反射获取代理类的构造器，创建实例
         */
         final Constructor<?> cons = cl.getConstructor(constructorParams);
         final InvocationHandler ih = h;
         return cons.newInstance(new Object[]{h});
    }
```
查找或者生成代理类
```
private static Class<?> getProxyClass0(ClassLoader loader,
                                           Class<?>... interfaces) {
        // If the proxy class defined by the given loader implementing
        // the given interfaces exists, this will simply return the cached copy;
        // otherwise, it will create the proxy class via the ProxyClassFactory
        /**从缓存里面获取代理类。如果缓存中没有，会创建代理类*/
        return proxyClassCache.get(loader, interfaces);
    }
```
WeakCache#get
```
//获取代理类
public V get(K key, P parameter) {
        Object cacheKey = CacheKey.valueOf(key, refQueue);
        // lazily install the 2nd level valuesMap for the particular cacheKey
        //懒惰策略设置二级map，如果二级map为null，就创建它
        ConcurrentMap<Object, Supplier<V>> valuesMap = map.get(cacheKey);
        if (valuesMap == null) {
            ConcurrentMap<Object, Supplier<V>> oldValuesMap
                = map.putIfAbsent(cacheKey,
                                  valuesMap = new ConcurrentHashMap<>());
            if (oldValuesMap != null) {
                valuesMap = oldValuesMap;
            }
        }
        //创建二级map的key
        // create subKey and retrieve the possible Supplier<V> stored by that
        // subKey from valuesMap
        Object subKey = Objects.requireNonNull(subKeyFactory.apply(key, parameter));
        Supplier<V> supplier = valuesMap.get(subKey);
        Factory factory = null;
        //从二级map中获取代理类工厂ProxyClassFactory
        while (true) {
            if (supplier != null) {
                // supplier might be a Factory or a CacheValue<V> instance
                //代理类工厂生成代理类
                V value = supplier.get();
                if (value != null) {
                    return value;
                }
            }
            // else no supplier in cache
            // or a supplier that returned null (could be a cleared CacheValue
            // or a Factory that wasn't successful in installing the CacheValue)

            // lazily construct a Factory
            //如果代理类工厂为null（弱引用，gc时被回收），就创建一个Factory，用来提供代理类（实际上还是从二级map中取）
            //Factory中的get方法是同步的，这样先走无锁逻辑，再走有锁逻辑，减少用锁开销。
            if (factory == null) {
                factory = new Factory(key, parameter, subKey, valuesMap);
            }

            if (supplier == null) {
                supplier = valuesMap.putIfAbsent(subKey, factory);
                if (supplier == null) {
                    // successfully installed Factory
                    supplier = factory;
                }
                // else retry with winning supplier
            } else {
                if (valuesMap.replace(subKey, supplier, factory)) {
                    // successfully replaced
                    // cleared CacheEntry / unsuccessful Factory
                    // with our Factory
                    supplier = factory;
                } else {
                    // retry with current supplier
                    supplier = valuesMap.get(subKey);
                }
            }
        }
    }
```
代理类工厂ProxyClassFactory核心逻辑
```
@Override
public Class<?> apply(ClassLoader loader, Class<?>[] interfaces) {
    Map<Class<?>, Boolean> interfaceSet = new IdentityHashMap<>(interfaces.length);
    String proxyPkg = null;     // package to define proxy class in
    /*
     * Generate the specified proxy class.
     * 使用字节码工程创建代理类
     */
    byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
        proxyName, interfaces, accessFlags);
    try {
    //使用类加载器加载动态创建的代理类
        return defineClass0(loader, proxyName,
                            proxyClassFile, 0, proxyClassFile.length);
    } catch (ClassFormatError e) {
        throw new IllegalArgumentException(e.toString());
    }
}
```
总结：