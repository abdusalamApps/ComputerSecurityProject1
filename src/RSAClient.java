import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class RSAClient {
    private BigInteger p;
    private BigInteger q;
    private BigInteger e;
    private BigInteger d;
    // p*q
    private BigInteger n;
    // (p-1)*(q-1)
    private BigInteger m;

    private List<Node> eCalculation;
    private List<String> dCalculation;
    private List<RemainderEquation> remainderEquations;

    public RSAClient(BigInteger p, BigInteger q) {
        this.p = p;
        this.q = q;
        this.m = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        this.n = p.multiply(q);
        this.e = findE();
        eCalculation = new ArrayList<>();
        dCalculation = new ArrayList<>();
        remainderEquations = new ArrayList<>();
        System.out.println("m ((p - 1) * (q - 1)) = " + m);
        System.out.println("n (p * q) = " + n);
        System.out.println("Possible e = " + e);

    }

    private BigInteger findE() {
        BigInteger e = BigInteger.TWO;
        while (!this.m.gcd(e).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.ONE);
        }
        return e;
    }

    public boolean testE(BigInteger e) {
        return m.gcd(e).equals(BigInteger.ONE);
    }

    public boolean testEManually(BigInteger e) {
        System.out.println("-----------------Testing " + e + " as e-----------------");
        BigInteger dividend = m;
        BigInteger divisor = e;
        BigInteger multiplier = dividend.divide(divisor);
        BigInteger remainder = dividend.mod(divisor);
        eCalculation.add(new Node(dividend, divisor, multiplier, remainder));
        while (!remainder.equals(BigInteger.ZERO)) {
            dividend = divisor;
            divisor = remainder;
            multiplier = dividend.divide(divisor);
            remainder = dividend.mod(divisor);
            eCalculation.add(new Node(dividend, divisor, multiplier, remainder));
        }
        boolean gcd1 = false;
        if (eCalculation.size() > 1)
            gcd1 = eCalculation.get(eCalculation.size() - 2).remainder.equals(BigInteger.ONE);
        else
            gcd1 = eCalculation.get(0).remainder.equals(BigInteger.ONE);
        eCalculation.forEach(System.out::println);
        System.out.print("=> ");
        System.out.println(gcd1 ? e + " works as e" : e + " does not work as e");
        eCalculation.clear();
        return gcd1;
    }

    public BigInteger calculateD() {
        System.out.println("-----------------Calculating d------------------");
        BigInteger d = BigInteger.ONE;
        Node oneNode = eCalculation.get(eCalculation.size() - 2);
        String oneEquation =
                oneNode.remainder + " = " + oneNode.dividend + " - " + oneNode.divisor + " * " + oneNode.multiplier;
        ;
        for (int i = eCalculation.size() - 2; i > 0; i--) {
            Node node = eCalculation.get(i);
            RemainderEquation remainderEquation = new RemainderEquation(node.remainder,
                    node.dividend + " - " + node.divisor + " * " + node.multiplier);
            remainderEquations.add(remainderEquation);
        }
        remainderEquations.forEach(System.out::println);
        for (RemainderEquation re : remainderEquations) {
            if (re.leftSide.equals(BigInteger.ONE)) continue;
            System.out.print("Replace (" + re.leftSide + ") => ");
            oneEquation = oneEquation.replace(re.leftSide.toString(), "(" + re.rightSide + ")");
            System.out.println(oneEquation);
        }

//        dCalculation.forEach(System.out::println);
        return d;
    }

    public BigInteger encrypt(BigInteger message) {
        System.out.println("-----------------Encrypting message(" + message + ")------------------------");
        return message.pow(Integer.parseInt(e.toString())).mod(n);
    }

    public String simplifyExpression(String expression) {
        String se = expression;
        System.out.println("--------------------Simplifying: " + expression + "------------------------");
        while (se.contains("(")) {
            int op = expression.indexOf('(');
            int cp = expression.indexOf(')');
            String inParentheses = expression.substring(op + 1, cp);
            String newInParentheses = inParentheses;
            System.out.println("inParentheses = " + inParentheses + "\n");
            newInParentheses = simplifyHelper(inParentheses);
            se = se.replace(inParentheses, newInParentheses);
/*
            while (inParentheses.contains("*") || inParentheses.contains("/") || inParentheses.contains("÷")
                    || inParentheses.contains("+") || inParentheses.contains("-")) {

                String operation = "*";
                if (inParentheses.contains("*")) operation = "*";
                if (inParentheses.contains("/")) operation = "/";
                if (inParentheses.contains("÷")) operation = "÷";
                if (inParentheses.contains("+")
                        && !(inParentheses.contains("*") || inParentheses.contains("/")))
                    operation = "+";
                if (inParentheses.contains("-")
                        && !(inParentheses.contains("*") || inParentheses.contains("/")))
                    operation = "-";

                int xPos = inParentheses.indexOf(operation);

                String leftSide = parseLeft(inParentheses, xPos);
                System.out.println("leftSide = " + leftSide);

                String rightSide = parseRight(inParentheses, xPos);
                System.out.println("rightSide = " + rightSide);

                String replaceTarget = leftSide + " " + operation + " " + rightSide;
                System.out.println("replaceTarget = " + replaceTarget);
                newInParentheses = inParentheses.replace(replaceTarget,
                        String.valueOf(operateExpression(operation, leftSide, rightSide)));

                se = se.replace(inParentheses, newInParentheses);
                inParentheses = newInParentheses;
                System.out.println("inParentheses = " + inParentheses);
                System.out.println("se = " + se + "\n");

            }
*/

            int firstOP = se.indexOf("(");
            int firstCP = se.indexOf(")");
            String firstOPString = se.substring(firstOP, firstOP + 1);
            String firstCPString = se.substring(firstCP, firstCP + 1);
            se = se.replace(firstOPString, "");
            se = se.replace(firstCPString, "");
            System.out.println("se = " + se);
        }
        se = simplifyHelper(se);

        return se;
    }


    private String simplifyHelper(String expression) {
        String se = expression;
        while (se.contains("*") || se.contains("/") || se.contains("÷")
                || se.contains("+") || se.contains("-")) {
            String operation = "*";
            if (se.contains("*")) operation = "*";
            if (se.contains("/")) operation = "/";
            if (se.contains("÷")) operation = "÷";
            if (se.contains("+")
                    && !(se.contains("*") || se.contains("/")))
                operation = "+";
            if (se.contains("-")
                    && !(se.contains("*") || se.contains("/")))
                operation = "-";

            int xPos = se.indexOf(operation);

            String leftSide = parseLeft(se, xPos);
            System.out.println("leftSide = " + leftSide);

            String rightSide = parseRight(se, xPos);
            System.out.println("rightSide = " + rightSide);

            se = se.replace(leftSide + " " + operation + " " + rightSide,
                    String.valueOf(operateExpression(operation, leftSide, rightSide)));
            System.out.println();
        }
        return se;
    }

    public int operateExpression(String operation, String left, String right) {
        return switch (operation) {
            case "*" -> Integer.parseInt(left) * Integer.parseInt(right);
            case "÷", "/" -> Integer.parseInt(left) / Integer.parseInt(right);
            case "+" -> Integer.parseInt(left) + Integer.parseInt(right);
            case "-" -> Integer.parseInt(left) - Integer.parseInt(right);
            default -> -1;
        };
    }

    public String parseLeft(String expression, int operationPosition) {
        int rightBound = operationPosition - 1;
        int leftBound = operationPosition - 2;
        boolean end = false;
        while (!end && leftBound != 0 && rightBound < expression.length()) {
            try {
                Integer.parseInt(expression.substring(leftBound, rightBound));
                leftBound--;
            } catch (NumberFormatException e) {
                end = true;
            }
        }
        return expression.substring(leftBound, rightBound).trim();
    }

    public String parseRight(String expression, int operationPosition) {
        int leftBound = operationPosition + 2;
        int rightBound = operationPosition + 3;
        boolean end = false;
        while (!end && leftBound != 0 && rightBound < expression.length()) {
            try {
                Integer.parseInt(expression.substring(leftBound, rightBound));
                rightBound++;
            } catch (NumberFormatException e) {
                end = true;
            }
        }
        return expression.substring(leftBound, rightBound).trim();
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

    public BigInteger getN() {
        return n;
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

        @Override
        public String toString() {
            return dividend + " = " + divisor + " * " + multiplier + " + " + remainder;
        }
    }

    class RemainderEquation {
        BigInteger leftSide;
        String rightSide;

        public RemainderEquation(BigInteger leftSide, String rightSide) {
            this.leftSide = leftSide;
            this.rightSide = rightSide;
        }

        @Override
        public String toString() {
            return leftSide + " = " + rightSide;
        }
    }
}
