import java.math.BigDecimal;
import java.math.BigInteger;

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
//            System.out.println("exp = " + exp);
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

}
