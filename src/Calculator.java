package src;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

/**
 * 计算器核心执行类
 *
 * 题目：
 * 写一个计算器类（Calculator），可以实现两个数的加、减、乘、除运算，并可以进行undo和redo操作，
 * 侯选人可在实现功能的基础上发挥最优设计，编写后请提供代码或者github地址
 */
public class Calculator {
    
    final static String SUM = "+";
    final static String SUB = "-";
    final static String MUL = "*";
    final static String DIV = "/";

    final static Stack<String> undoStack = new Stack<>();
    static String buffer = null;
    final static Stack<String> redoStack = new Stack<>();

    static boolean acceptTempInput = true;
    static String tempInput = null;

    public static void main(String[] args) {
//        Scanner scanner=new Scanner(System.in);
//
//        while (scanner.hasNextLine()) {
//            String row = scanner.nextLine();
//            processInput(row);
//        }
    }

    public static String processUndo(String nowInput) {
        if (undoStack.isEmpty()) {
            if (buffer != null) {
                return buffer;
            }
            return null;
        }

        if (acceptTempInput && nowInput != null) {
            tempInput = nowInput;
            acceptTempInput = false;
        }
        String a = undoStack.pop();
        if (buffer != null) {
            redoStack.push(buffer);
        }
        buffer = a;
        return a;
    }

    public static String processRedo() {
        if (buffer != null) {
            undoStack.push(buffer);
            buffer = null;
        }

        if (redoStack.isEmpty()) {
            acceptTempInput = true;
            if (tempInput != null) {
                return tempInput;
            }
            return "";
        }

        String a = redoStack.pop();

        buffer = a;
        return a;
    }

    public static String processInput(String row) {
        if (buffer != null) {
            undoStack.push(buffer);
            buffer = null;
        }
        while (!redoStack.isEmpty()) {
            undoStack.push(redoStack.pop());
        }
        undoStack.push(row);

        if (row.contains(SUM)) {
            return process(row, SUM);
        }

        if (row.contains(SUB)) {
            return process(row, SUB);
        }

        if (row.contains(MUL)) {
            return process(row, MUL);
        }

        if (row.contains(DIV)) {
            return process(row, DIV);
        }

        return "错误的输入，不包含运算符";
    }
    
    public static String process(String row, String c) {
        int indexOfC = row.indexOf(c);

        BigDecimal x, y;
        try {
            x = new BigDecimal(row.substring(0, indexOfC).trim());
            y = new BigDecimal(row.substring(indexOfC + 1).trim());
        } catch (Exception e) {
            return "错误的表达式";
        }
        
        switch (c) {
            case "+" : return sum(x, y);
            case "-" : return sub(x, y);
            case "*" : return mul(x, y);
            case "/" : return div(x, y);
            default:
                return "错误的运算符";
        }
        
    }
    
    public static String sum(BigDecimal a,BigDecimal b){
        BigDecimal r = a.add(b);
        return r.toString();
    }
    public static String sub(BigDecimal a,BigDecimal b){
        BigDecimal r = a.subtract(b);
        return r.toString();
    }
    public static String mul(BigDecimal a,BigDecimal b){
        BigDecimal r = a.multiply(b);
        return r.toString();
    }
    public static String div(BigDecimal a,BigDecimal b){
        if (BigDecimal.ZERO.equals(b)) {
            return "除数不能为0";
        }
        BigDecimal r = a.divide(b, 10, RoundingMode.HALF_UP);
        return r.toString();
    }
}
