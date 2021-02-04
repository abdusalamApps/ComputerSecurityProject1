import java.math.BigInteger;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        double t1 = System.currentTimeMillis();
        Random random = new Random();
        BigInteger n = new BigInteger("9389");
        BigInteger s = new BigInteger("390");
        BigInteger r = new BigInteger("382");

        for (int i = 0; i < 20; i++) {
            int nInt = n.intValue();
            int a = random.nextInt(nInt);
            RabinMiller rabinMiller = new RabinMiller(
                    BigInteger.valueOf(a),
                    n, s, r.intValue()
            );
            String pString = rabinMiller.isPrime() ? "prime" : "not prime";
            System.out.println(n + " is " + pString + " for a = " + a);
        }
        double t2 = System.currentTimeMillis();
        System.out.println("Time taken = " + (t2 - t1) + " ms");
    }
}
