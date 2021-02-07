import java.math.BigInteger;
import java.util.Random;

public class Main {

    static BigInteger p = new BigInteger("10005082542311079080686723161199363566471332445932428610467078285318560831241339722384275927300588434266668675680333439605585119940611400536867015774070183");
    static BigInteger q = new BigInteger("9044885983718200171901312673813076120582336544059703504207583137475214747369938094566323622939426704145907328525869878966177496740710480651558672871475607");
    static BigInteger d = new BigInteger("10231879649967775986507368090236339350964096205927951481093046944196613935464910746288741388760549333581024650654056530715468627602243722804668880455899916938171042654475040031917978058374197233075744324105160150807333888303166197910015428096816331640419070523733676411948667902043937962848377144470624873033");

    public static void main(String[] args) {
        System.out.println("---------------testIsPrime() start--------------");
        testIsPrime();
        System.out.println("---------------testIsPrime() end-----------------");

        System.out.println();

        System.out.println("---------------testGeneratePrimes() start--------------");
        testGeneratePrimes();
        System.out.println("---------------testGeneratePrimes() end-----------------");

        System.out.println();

        System.out.println("---------------testInverseMod() start--------------");
        testInverseMod();
        System.out.println("---------------testInverseMod() end--------------");

        System.out.println();

        System.out.println("---------------testInverseModFixedE() start--------------");
        testInverseModFixedE();
        System.out.println("---------------testInverseModFixedE() end--------------");

        System.out.println("---------------getS() start--------------");
        System.out.println(getS());
        System.out.println("---------------getS() end--------------");

        System.out.println();

        System.out.println("---------------getC() start--------------");
        System.out.println(getC());
        System.out.println("---------------getC() end--------------");

        System.out.println();

        System.out.println("---------------getZ() start--------------");
        System.out.println(getZ());
        System.out.println("---------------getZ() end--------------");


    }

    public static void testIsPrime() {
        double t1 = System.currentTimeMillis();
        Random random = new Random();
        BigInteger n = new BigInteger("23");
        BigInteger s = new BigInteger("3");
        BigInteger r = new BigInteger("2");

        for (int i = 0; i < 20; i++) {
            int nInt = n.intValue();
            int a = random.nextInt(nInt);
            RabinMiller rabinMiller = new RabinMiller(
                    BigInteger.valueOf(a),
                    n, s, r.intValue()
            );
            String pString = rabinMiller.isPrime2() ? "prime" : "not prime";
            System.out.println(n + " is " + pString + " for a = " + a);
/*
            if (rabinMiller.isPrime()) {
                System.out.println(n + " is prime for a = " + a);
                break;
            }
*/
        }
        double t2 = System.currentTimeMillis();
        System.out.println("Time taken = " + (t2 - t1) + " ms");
    }

    public static void testGeneratePrimes() {
        BigInteger[] primes = RabinMiller.generatePrimes(100, 512);
        System.out.println(primes.length + " prime numbers found:");
        for (BigInteger p : primes) {
            System.out.println(p);
        }

    }

    // Assignment 4.1
    public static void testInverseMod() {
        BigInteger[] bigIntegers = RabinMiller.generatePrimes(2, 4);
        try {
            BigInteger inverse = RabinMiller.inverseMod(bigIntegers[0], bigIntegers[1]);
            System.out.println("inverse = " + inverse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Assignment 4.2
    public static void testInverseModFixedE() {
        try {
            BigInteger inverse = RabinMiller.inverseModFixedE(p, q);
            System.out.println("inverse = " + inverse);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Assignment 5.1
    public static BigInteger getS() {
        Random random = new Random();
        BigInteger n = p.multiply(q);
        BigInteger s = new BigInteger(n.bitLength(), random);
        while (s.compareTo(BigInteger.ZERO) <= 0 || s.compareTo(n) > 0) {
            s = new BigInteger(n.bitLength(), random);
        }
        return s;
    }

    // Assignment 5.2
    public static BigInteger getC() {
        BigInteger e = RabinMiller.generateE(p, q);
        BigInteger n = p.multiply(q);
       return BigInteger.ZERO.modPow(e, n);
    }

    // Assignment 5.3
    public static BigInteger getZ() {
        BigInteger n = p.multiply(q);
        BigInteger z = BigInteger.ZERO;
        try {
            z = getC().modPow(RabinMiller.inverseModFixedE(p, q), n);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }
}
