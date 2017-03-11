/**
 * Factor counter class
 * Created by rom on 01.11.16.
 */
public class Factor {
    static private long FACTORIAL_START = 1;
    public static long getFactorial(long i) {
        return factor(i, FACTORIAL_START);
    }

    private static long factor(long i, long f) {
        if (i == 0) return f;
        f = f * i;
        i--;
        return factor(i, f);
    }
}
