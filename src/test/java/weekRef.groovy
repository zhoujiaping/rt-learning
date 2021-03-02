import java.lang.ref.WeakReference

def wr = new WeakReference(new String("hello"))
println wr.get()
System.gc() //通知JVM的gc进行垃圾回收
println wr.get()
