# java8定义的一些函数式接口
## 主要分为以下几类
1. Consumer 消费者
    * void accept(T t); 
      
      消费一个参数
    
    * default Consumer<T> andThen(Consumer<? super T> after); 
    
    如果有两个消费者，可以用andThen将消费者串起来，包装成一个消费者。
    这样参数可以按顺序被两个消费者消费。
    多余两个消费者的情况可以递归用上面的思想。
   
2. Function 函数
    * R apply(T t);

    接收一个参数，返回一个值。

    * default <V> Function<V, R> compose(Function<? super V, ? extends T> before);
    
    组合。组合函数V->T得到V->R的函数。
   比如我们需要取字符串“hello”的首字符，然后转成大写。
   可以定义两个函数，firstChar和toUpper，然后调用toUpper的compose，得到一个组合函数。

    * default <V> Function<T, V> andThen(Function<? super R, ? extends V> after);
    同compose，区别就是组合的顺序。

    * static <T> Function<T, T> identity();
    这个函数的主要作用是将值包装成函数。
    
3. Predicate：断言
    * boolean test(T t);

   接收一个参数，返回一个boolean值。

    * default Predicate<T> and(Predicate<? super T> other);
    使用与逻辑组合另一个断言，得到一个新断言。
      
    * default Predicate<T> negate();
    得到反向断言。

    * default Predicate<T> or(Predicate<? super T> other); 
    使用或逻辑组合另一个断言，得到一个新断言。

    * static <T> Predicate<T> isEqual(Object targetRef);
    得到一个对象的equals断言。注意：如果两个对象都为null，得到的断言结果为true。

4. Supplier：供应者
    * T get();
    没有参数，返回一个值。

5. 二元接口
   
   对于上面的接口，都是最多接收一个参数，如果需要两个参数，可以用上面的接口对应的二元接口。
   
   如果多余两个参数，可以使用对应接口的组合方法。
   
   这样的设计，估计是参考了haskell。
   
   在haskell中，所有的纯函数都是一元的。
   
   多元函数实际上也是一元函数。
   
   比如haskell中定义函数add=x->y->x+y，调用的时候效果上可以当成接受x和y返回和。
   实际上是先接受x返回一个函数，再调用一次函数得到和。
   haskell中函数调用不需要圆括号，调用形式如add 1 2，看上去好像是给add传递两个参数，实际上是add 1得到一个函数-假设为add1，
   然后再调用add1 2。

    二元接口，比如BiFunction，是BinaryFunction的缩写。

