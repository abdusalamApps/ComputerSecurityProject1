import java.math.BigInteger;
import java.util.List;

public class GeneratePrimes {
    public static void main(String[] args) {
        List<BigInteger> primes = RabinMiller.generatePrimes(3, 28);
        System.out.println(primes.size() + " prime numbers found:");
        for (BigInteger p : primes) {
            System.out.print(p + " (" + p.bitCount() + " bits)" + ", ");
        }

    }
}
