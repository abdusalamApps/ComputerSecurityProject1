import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RabinMiller {
    private BigInteger a;
    private BigInteger n;
    private BigInteger s;
    private int r;

    public RabinMiller(BigInteger a, BigInteger n, BigInteger s, int r) {
        this.a = a;
        this.n = n;
        this.s = s;
        this.r = r;
    }

    private boolean isFirstCondition() {
        return !a.modPow(s, n).equals(n.add(BigInteger.ONE));
    }

    private boolean isSecondCondition() {
        double exp;
        BigInteger bExp;
        for (int j = 0; j <= r - 1; j++) {
            exp = (Math.pow(2, j) * s.doubleValue());
            BigDecimal bigDecimalExp = BigDecimal.valueOf(exp);
            bExp = bigDecimalExp.toBigInteger();
            if (a.modPow(bExp, n).equals(n.subtract(BigInteger.ONE))) {
                return false;
            }
        }
        return true;
    }

    private boolean isSecondCondition2() {
        BigInteger x = a.modPow(s, n);
        for (int i = 0; i < r - 1; i++) {
            int jPow2 = (int) Math.pow(2, i);
            BigInteger x1 = x.modPow(BigInteger.valueOf(jPow2), n);
            if (x1.equals(n.subtract(BigInteger.ONE)))
                return false;
        }
        return true;
    }

    public boolean isPrime() {
        return !(isFirstCondition() && isSecondCondition());
    }

    public boolean isPrime2() {
        return !(isFirstCondition() && isSecondCondition2());
    }


    public BigInteger[] generateTwoPrimes(int length) {
        BigInteger[] twoPrimes = new BigInteger[2];


        return twoPrimes;
    }

    public static List<BigInteger> generatePrimes(int count, int bitLength) {
        List<BigInteger> primes = new ArrayList<>();

        double t1 = System.currentTimeMillis();
        Random random = new Random();
        BigInteger n = BigInteger.probablePrime(512, random);
        BigInteger s = new BigInteger("3");
        BigInteger r = new BigInteger("2");

        while (primes.size() < count) {
            for (int i = 0; i < 10; i++) {
                System.out.println("n = " + n.intValue());
                int a = 1 + random.nextInt(n.intValue() < 0 ? n.intValue() * -1 : n.intValue());
                RabinMiller rabinMiller = new RabinMiller(
                        BigInteger.valueOf(a),
                        n, s, r.intValue()
                );

                if (rabinMiller.isPrime()) {
                    primes.add(n);
                    break;
                }
            }
            n = BigInteger.probablePrime(512, random);
        }
        double t2 = System.currentTimeMillis();
        System.out.println("Time taken = " + (t2 - t1) + " ms");

        return primes;
    }

    public static Byte[] bytesArray(int bitLength) {
        Byte[] bytes = new Byte[bitLength/8];

        return bytes;
    }
}
