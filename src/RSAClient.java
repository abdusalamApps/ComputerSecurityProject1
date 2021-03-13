import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;

public class RSAClient {
    private BigInteger p;
    private BigInteger q;
    private BigInteger e;
    private BigInteger d;
    // p*q
    private BigInteger n;
    // (p-1)*(q-1)
    private BigInteger m;

    private ArrayList<Node> eCalculation;

    public RSAClient(BigInteger p, BigInteger q) {
        this.p = p;
        this.q = q;
        this.m = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        this.n = p.multiply(q);
        this.e = findE();
        eCalculation = new ArrayList<>();
    }

    private BigInteger findE() {
        BigInteger e = BigInteger.TWO;
        while (!this.m.gcd(e).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.ONE);
        }
        return e;
    }

/*
    private BigInteger calculateD() {

    }
*/
    public boolean testE(BigInteger e) {
        return m.gcd(e).equals(BigInteger.ONE);
    }

    public boolean testEManually(BigInteger e) {
        BigInteger dividend = m;
        BigInteger divisor = e;
        BigInteger multiplier = dividend.divide(divisor);
        BigInteger remainder = dividend.mod(divisor);
        eCalculation.add(new Node(dividend, divisor, multiplier, remainder));
        System.out.println(dividend + " = " + divisor + " x " + multiplier + " + " + remainder);
        while (!remainder.equals(BigInteger.ZERO)) {
            dividend = divisor;
            divisor = remainder;
            multiplier = dividend.divide(divisor);
            remainder = dividend.mod(divisor);
            eCalculation.add(new Node(dividend, divisor, multiplier, remainder));
            System.out.println(dividend + " = " + divisor + " x " + multiplier + " + " + remainder);
        }
        return eCalculation.get(eCalculation.size() - 2).remainder.equals(BigInteger.ONE);
    }


    public BigInteger encrypt(BigInteger message) {
        System.out.println("int e = " + Integer.parseInt(e.toString()));
        return message.pow(Integer.parseInt(e.toString())).mod(n);
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getM() {
        return m;
    }

    public void setE(BigInteger e) {
        if (testE(e)) {
            this.e = e;
        } else {
            System.out.println(e + " does not work as e");
        }
    }

    class Node {
        BigInteger dividend;
        BigInteger divisor;
        BigInteger multiplier;
        BigInteger remainder;

        public Node(BigInteger dividend, BigInteger divisor, BigInteger multiplier, BigInteger remainder) {
            this.dividend = dividend;
            this.divisor = divisor;
            this.multiplier = multiplier;
            this.remainder = remainder;
        }
    }
    public static void main(String[] args) {
        RSAClient rsaClient = new RSAClient(
                new BigInteger("149"),
                new BigInteger("197"));
        System.out.println("m = " + rsaClient.getM());
        System.out.println("e = " + rsaClient.getE());
/*
        System.out.println(
                rsaClient.testE(new BigInteger("253"))
                        ? "253 works as e" : "253 does not work as e");
*/
        System.out.println(
                rsaClient.testEManually(new BigInteger("253"))
                        ? "253 works as e" : "253 does not work as e");
        rsaClient.setE(new BigInteger("253"));
        System.out.println(rsaClient.encrypt(new BigInteger("3")));

/*
        BigInteger dividend = new BigInteger("10");
        BigInteger divisor = new BigInteger("3");
        System.out.println("multiplier = " + dividend.divide(divisor));
        System.out.println("remainder = " + dividend.mod(divisor));
*/
    }

}
