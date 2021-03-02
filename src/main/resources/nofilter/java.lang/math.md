Math中定义了一些常用的数学函数，这没什么好说的。
下面主要介绍一些不太常用的函数。
```
//求立方根。
public static double cbrt(double a);
//求和，如果溢出则抛异常。
public static int addExact(int x, int y);
//返回 sqrt(x^2+y^2) 没有中间溢或下溢
public static double hypot(double x, double y);
//将浮点数或双精度数转换为浮点表示形式。该方法从表示中返回指数部分。
public static int getExponent(float f);
//在第二个参数的方向上返回与第一个参数相邻的数字。
public static double nextAfter(double start, double direction);
//在正无穷大的方向上返回与指定参数相邻的数字。
public static double nextUp(double d);
//在正无穷小的方向上返回与指定参数相邻的数字。
public static double nextDown(double d);
//同f*Math.pow(2, scaleFactor)，性能上比pow好。
//一些编译器和 VM 的智能程度比较高。
//一些优化器会将 x*Math.pow(2, y)识别为特殊情况并将其转换为Math.scalb(x, y)或类似的东西。
public static float scalb(float f, int scaleFactor);
//返回小于或等于x的2的最大幂。
static double powerOfTwoD(int n);
```
