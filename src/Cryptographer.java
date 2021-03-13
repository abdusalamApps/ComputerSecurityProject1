import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Cryptographer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("p?");
        String p = scanner.nextLine();
        System.out.println("q?");
        String q = scanner.nextLine();
        RSAClient rsaClient = new RSAClient(
                new BigInteger(p),
                new BigInteger(q));

        boolean gcd1 = false;
        do {
            System.out.println("e?");
            String eString = scanner.nextLine();
            BigInteger e = new BigInteger(eString);
            gcd1 = rsaClient.testEManually(e);
            if (gcd1) rsaClient.setE(e);
        } while (!gcd1);

        System.out.println("message?");
        BigInteger message = new BigInteger(scanner.nextLine().trim());
        System.out.println("Cipher = " + rsaClient.encrypt(message));
/*
        rsaClient.calculateD();

        System.out.println(rsaClient.simplifyExpression("8 - (79 - 8 * 9) * 1"));
*/

    }

}
