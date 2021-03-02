import java.lang.ref.SoftReference

def name = new String("jack")
def sr = new SoftReference(name)
name = null
println sr.get()//可能为null