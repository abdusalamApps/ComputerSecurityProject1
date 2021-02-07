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

    public static BigInteger[] generatePrimes(int count, int bitLength) {
        Random random = new Random();
        BigInteger[] primes = new BigInteger[count];
        double t1 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            primes[i] = BigInteger.probablePrime(bitLength, random);
        }
        double t2 = System.currentTimeMillis();
        System.out.println("Time taken = " + (t2 - t1) + " ms");
        return primes;
    }

    public static BigInteger generateE(BigInteger p, BigInteger q) {
        BigInteger m = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.TWO;
        while (!m.gcd(e).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.ONE);
        }
        return e;
    }

    public static BigInteger inverseMod(BigInteger p, BigInteger q) throws Exception {
        BigInteger a = RabinMiller.generateE(p, q);
        BigInteger qn = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger v1 = BigInteger.ZERO;
        BigInteger v2 = BigInteger.ONE;
        BigInteger d1 = qn;
        BigInteger d2 = a;

        while (!d2.equals(BigInteger.ZERO)) {
            q = d1.divide(d2);
            BigInteger t2 = v1.subtract(q.multiply(v2));
            BigInteger t3 = d1.subtract(q.multiply(d2));
            v1 = v2;
            d1 = d2;
            v2 = t2;
            d2 = t3;
        }
        BigInteger v = v1;
        BigInteger d = d1;
        if (d.equals(BigInteger.ONE)) {
            return v;
        } else {
            throw new Exception("(a mod m) has no inverse");
        }
    }

    public static BigInteger inverseModFixedE(BigInteger p, BigInteger q) throws Exception {
        BigInteger a = BigInteger.TWO.pow(16).add(BigInteger.ONE);
        BigInteger qn = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger v1 = BigInteger.ZERO;
        BigInteger v2 = BigInteger.ONE;
        BigInteger d1 = qn;
        BigInteger d2 = a;

        while (!d2.equals(BigInteger.ZERO)) {
            q = d1.divide(d2);
            BigInteger t2 = v1.subtract(q.multiply(v2));
            BigInteger t3 = d1.subtract(q.multiply(d2));
            v1 = v2;
            d1 = d2;
            v2 = t2;
            d2 = t3;
        }
        BigInteger v = v1;
        BigInteger d = d1;
        if (d.equals(BigInteger.ONE)) {
            return v;
        } else {
            throw new Exception("(a mod m) has no inverse");
        }
    }

}
