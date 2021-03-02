import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IntTest {
    @Test
    public void test() throws IOException {
        String num = Files.readAllLines(Paths.get("d:/test.txt")).get(0);
        assert 512==Integer.parseInt(num);
        assert Integer.parseInt(num)==512;
    }
}
