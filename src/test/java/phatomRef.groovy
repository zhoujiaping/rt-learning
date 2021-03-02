import java.lang.ref.PhantomReference
import java.lang.ref.ReferenceQueue

def queue = new ReferenceQueue<String>()
def pr = new PhantomReference<String>(new String("hello"), queue)
println pr.get()
