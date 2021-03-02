instrument是jdk5开始提供的一种拓展机制，允许用户在jvm加载类之前对字节码进行替换。
jdk6对instrument又进行了增强，通过 Java Tool API 中的 attach 方式，
我们可以很方便地 在运行过程中动态地设置加载代理类，以达到 instrumentation 的目的。

阿里巴巴基于instrument实现arthas。
idea基于instrument实现远程调试。
spring基于instrument实现类动态加载。

instrument这个包的类不多，具体使用，请自行用搜索引擎。

案例 https://github.com/zhoujiaping/java-agent
