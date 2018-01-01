package engineer.echo.transition;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void print9() {
        System.out.println("99乘法表");
        //i控制每行算式个数，j控制共有多少行。
        for (int i = 1, j = 1; j <= 9; i++) {
            System.out.printf("%d*%d=%d ", i, j, i * j);
            //当i = j 这一行输入结束，换行。再把i置0。
            if (i == j) {
                i = 0;
                j++;
                System.out.printf("\n");
            }
        }
    }
}