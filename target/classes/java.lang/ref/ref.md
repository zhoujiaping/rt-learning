# java中有四种引用。
分别为强引用，软引用，弱引用，虚引用。

Java中提供这四种引用类型主要有两个目的：
第一是可以让程序员通过代码的方式决定某些对象的生命周期；第二是有利于JVM进行垃圾回收。

## 强引用：比如 ```Object object = new Object();```
对于强引用，只有在对象不可达时才可能被回收。

## 软引用: 对于软引用关联着的对象，只有在内存不足的时候JVM才会回收该对象。

```
def name = new String("jack")
def sr = new SoftReference(name)
name = null
println sr.get()//可能为null
```
## 弱引用：
当JVM进行垃圾回收时，无论内存是否充足，都会回收被弱引用关联的对象。
在java中，用java.lang.ref.WeakReference类来表示。
```
def wr = new WeakReference(new String("hello"))
println wr.get()
System.gc() //通知JVM的gc进行垃圾回收
println wr.get()
```

## 虚引用：
虚引用和前面的软引用、弱引用不同，它并不影响对象的生命周期。
在java中用java.lang.ref.PhantomReference类表示。
如果一个对象与虚引用关联，则跟没有引用与之关联一样，在任何时候都可能被垃圾回收器回收。
虚引用主要用来跟踪对象被垃圾回收的活动。

虚引用必须和引用队列关联使用，当垃圾回收器准备回收一个对象时，
如果发现它还有虚引用，就会把这个虚引用加入到与之 关联的引用队列中。
程序可以通过判断引用队列中是否已经加入了虚引用，来了解被引用的对象是否将要被垃圾回收。
如果程序发现某个虚引用已经被加入到引用队列，
那么就可以在所引用的对象的内存被回收之前采取必要的行动。

```
def queue = new ReferenceQueue<String>()
def pr = new PhantomReference<String>(new String("hello"), queue)
println pr.get()
```

# 应用
mybatis中用弱引用实现缓存。
jdk动态代理里面用弱引用缓存代理类。
spring的beanutils用弱引用缓存反射信息。


