Integer一些不太常用的方法
```
返回二进制最高为的1表示的值
public static int highestOneBit(int i);
返回二进制最低为的1表示的值
public static int lowestOneBit(int i);
返回整型i的最高非零位前面的0的个数，包括符号位在内；
public static int numberOfLeadingZeros(int i);
返回指定int值的二进制补码二进制表示形式中最低顺序（“最右”）一位之后的零位数目。
public static int numberOfTrailingZeros(int i);
数值在二进制下“1”的数量。
public static int bitCount(int i);
循环左移
public static int rotateLeft(int i, int distance);
循环右移
public static int rotateRight(int i, int distance);
二进制位反转
public static int reverse(int i);
字节反转（反转字节序）
public static int reverseBytes(int i);

将-128到127的值缓存。assert Integer.valueOf(127)==Integer.valueOf(127)
这是使用享元模式，减少内存使用。（redis里面也用到享元模式，它会在启动时创建0-9999数值对应的字符串。）
private static class IntegerCache
```
总结：Integer类提供了一些非常高效的二进制操作。
