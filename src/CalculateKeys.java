import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by AndreiM on 12/21/2016.
 * Calculates keys based on my UoBNumber:
 * For every k, 0 < k <= 16, the k-th key is calculated using this formula: Fib(100 + k) mod UoB, where
 * Fib(100 + k) = the 100 + k-th number in the Fibonacci series and UoB = UoB Number
 * It finds first the 100th and 101th element in the Fibonacci series, and calculates the others after, to save time.
 * Probably not the best algorithm that exists(I've seen some using matrices or other complicated stuff), but it is better than the pure recursive one
 * It is used BigInteger to hold the large numbers
 */
public class CalculateKeys {

    private static final BigInteger UOBNUMBER = new BigInteger("15007406");


    public static String calculate() throws IOException{
            String outToFile;
            /**
             * Calculating 2 of the Fibonacci numbers
             * prevFib, nextFib and result are used to calculate the Fibonacci numbers, while key holds one of the keys (pretty straightforward)
             */
            BigInteger prevFib = fibonacci(100);
            BigInteger nextFib = fibonacci(101);


            BigInteger result;
            BigInteger key;
            /**
             * the first key (k = 1) and its transition to file
             */
            key = nextFib.mod(UOBNUMBER);
            outToFile = "" + key;
            /**
             * Calculate the keys k = 2 to 16
             * It's the same algorithm as in the fibonacci method, the difference being that after the current fibonacci number is found,
             * the key will be calculated as well.
             */
            for (int i = 1; i< 16; i++) {
                result = prevFib.add(nextFib);
                prevFib = nextFib;
                nextFib = result;
                key = nextFib.mod(UOBNUMBER);
                outToFile += ", " + key ;
            }// end for
            return outToFile;
    }
    /**
     * Finds the n-th number in the fibonacci series and returns it.
     */
    public static BigInteger fibonacci (int n) {
        BigInteger prev = new BigInteger("0");
        BigInteger result = new BigInteger("0");
        BigInteger next = new BigInteger("1");
        for (int i=2; i<= n; i++) {
            result = prev.add(next);
            prev = next;
            next = result;
        }
        return result;
    }

    /**
     * Sorts the keys into ascending order
     * @param keys
     * @return
     */
    public static String sort(String keys) {
        String sortedKeys = "";
        String[] k  = keys.split(" ");
        BigInteger[] qwerty = new BigInteger[16];
        for (int i =0; i<16; i++) {
            qwerty[i] = new BigInteger(k[i]);
        }
        Arrays.sort(qwerty);
        for (int i =0; i<16; i++) {
            sortedKeys += qwerty[i] + " ";
        }
        return sortedKeys;
    }

    public static BigInteger getUobNumber() {
        return UOBNUMBER;
    }
}
