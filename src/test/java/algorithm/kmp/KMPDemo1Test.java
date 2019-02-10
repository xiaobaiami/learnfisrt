package algorithm.kmp;

import org.junit.Test;

import java.util.Arrays;

public class KMPDemo1Test {
    KMPDemo1 demo1 = new KMPDemo1();

    @Test
    public void getNextTest() {
        String str = "ababacd";
        int[] next = demo1.getNext(str);
        System.out.println(Arrays.toString(next));
    }

    @Test
    public void kmpContainsTest() {
        String str1 = "llababacdbablacdbabacd";
        String str2 = "ababacd";
        System.out.println(demo1.kmpContains(str1, str2));
    }

}
