import java.math.BigInteger;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        double t1 = System.currentTimeMillis();
        Random random = new Random();
        BigInteger n = new BigInteger("9641616707934984592195427775281547281187504072600036401893934243945408808268200906870619864183308348810025455669900768235817678474129018147671615645726349");
        BigInteger s = new BigInteger("390");
        BigInteger r = new BigInteger("382");

        for (int i = 0; i < 100; i++) {
            int nInt = n.intValue();
            int a = random.nextInt(nInt);
            RabinMiller rabinMiller = new RabinMiller(
                    BigInteger.valueOf(a),
                    n, s, r.intValue()
            );

/*
            String pString = rabinMiller.isPrime2() ? "prime" : "not prime";
            System.out.println(n + " is " + pString + " for a = " + a);
*/
            if (rabinMiller.isPrime()) {
                System.out.println(n + " is prime for a = " + a);
                break;
            }
        }
        double t2 = System.currentTimeMillis();
        System.out.println("Time taken = " + (t2 - t1) + " ms");
    }
}
