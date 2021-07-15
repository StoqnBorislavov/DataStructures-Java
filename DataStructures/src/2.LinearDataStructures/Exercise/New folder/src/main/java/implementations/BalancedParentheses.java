package implementations;

import interfaces.Solvable;

import java.util.ArrayDeque;

public class BalancedParentheses implements Solvable {
    private String parentheses;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {
        // [ { (
        ArrayDeque<String> deque = new ArrayDeque<>();
        for (int i = 0; i < this.parentheses.length(); i++) {
            String currentSymbol = this.parentheses.charAt(i) + "";
            if(currentSymbol.equals("{") || currentSymbol.equals("[") ||currentSymbol.equals("(")){
                deque.push(currentSymbol);
            } else if (currentSymbol.equals("}") || currentSymbol.equals("]") ||currentSymbol.equals(")")){
                if(deque.isEmpty()){
                    return false;
                }
                if(deque.peek().equals("{") && !currentSymbol.equals("}") || deque.peek().equals("[") && !currentSymbol.equals("]") || deque.peek().equals("(") && !currentSymbol.equals(")")){
                    return false;
                }
                deque.pop();
            }
        }
        return deque.size() == 0;
    }
}
