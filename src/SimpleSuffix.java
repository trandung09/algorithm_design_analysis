import java.util.Stack;

public class SimpleSuffix {

    private String[] suffixArray;

    public SimpleSuffix(String suffixString) {
        suffixArray = suffixString.split(" "); 
                                        // or regex = "\\s+"
    }
    
    /**
     * @param s is a string that checks if it is an operator
     * @return {@code true} if s is operator
     *         {@code false} otherwise
     */
    private boolean isOperator(String s) {
        if (s.equals("+") || s.equals("-") 
            || s.equals("*") || s.equals("/"))
            return true;
        else return false;
    }

    /**
     * Calculates the result from the given postfix expression
     * 
     * @return result of postfix expression
     */
    public double interpret() { // interpret = thong dich
        Stack<String> stack = new Stack<>();
        for (String s : suffixArray) {
            // if s is operator
            if (isOperator(s)) { 
                String right = stack.pop();
                String left = stack.pop();
                stack.push(calculate(s, left, right));
            }
            else {
                stack.push(s);
            }
        }
        return Double.parseDouble(stack.pop());
    }

    /**
     * Calculate the result of two digit with the given operator
     * 
     * @param s operator ( +, -, /, *)
     * @param left  first digit
     * @param right second digit
     * @return result of two digit with the given operator
     */
    private String calculate(String s, String left, String right) {
        double dLeft = Double.valueOf(left);
        double dRight = Double.valueOf(right);

        switch (s) {
        case "+": 
            return str(dLeft + dRight);
        case "-": 
            return str(dLeft - dRight);
        case "*": 
            return str(dLeft * dRight);
        case "/":
            return str(dLeft / dRight);
        }
        return null;
    }

    /**
     * Convert double data type to string
     * 
     * @param value double valye
     * @return string data type from double value
     */
    private String str(double value) {
        return String.valueOf(value);
    }

    public static void main(String[] args) {
        
        String suffixString = "2 3 + 4 * 4 5 + 6 3 - * +";
        SimpleSuffix simSuffix = new SimpleSuffix(suffixString);
        
        double result = simSuffix.interpret();
        System.out.println("Ket qua tinh \"" + suffixString + "\" la: " + result);
    }
}
