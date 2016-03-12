package aascanner;

import com.sun.org.apache.bcel.internal.generic.IFLE;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class AAScanner {


    public static boolean checkIfReserved(String str) {
        if (str.equalsIgnoreCase("IF") || str.equalsIgnoreCase("THEN") || str.equalsIgnoreCase("ELSE") || str.equalsIgnoreCase("REPEAT") || str.equalsIgnoreCase("END") || str.equalsIgnoreCase("UNTIL") || str.equalsIgnoreCase("READ") || str.equalsIgnoreCase("WRITE")) {
            return true;
        } else {
            return false;
        }

    }
    public static boolean checkIfComment(char cr)
    {
    if (cr=='{')
        return true;
    else
        return false;
    
    }
    public static String SymbolName(char cr) {
        switch (cr) {
            case '+':
                return "PLUS";
            case '-':
                return "MINUS";
            case '*':
                return "Multiply";
            case '/':
                return "Backslash";
            case '=':
                return "Equals";
            case '<':
                return "Smaller Than";
            case '(':
                return "Left Bracket";
            case ')':
                return "Right Bracket";
            default:
                return "NOT DEFINED";

        }

    }

    public static boolean checkIfAssign(char cr) {
        if (cr == ':') {
            return true;
        }
        return false;
    }

    public static boolean checkIfLetter(char cr) {
        return Character.isLetter(cr);

    }

    public static boolean checkIfDigit(char cr) {
        return Character.isDigit(cr);
    }

    public static boolean checkIfWhite(char cr) {

        return Character.isWhitespace(cr);
    }

    public static boolean checkIfSymbol(char cr) {
        if (cr=='}' || cr=='{' || cr == '+' || cr == '-' || cr == '*' || cr == '/' || cr == '=' || cr == '<' || cr == '(' || cr == ')' || cr == ':' || cr == '=') {
            return true;
        } else {
            return false;
        }

    }

    public static void state1(char cr) {

    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Line Of Code");
        String lineofcode = sc.nextLine();
        Stack stack = new Stack();

        int i = 0;

        while (lineofcode.charAt(i) != ';') {

            if (checkIfLetter(lineofcode.charAt(i))) {
                while (checkIfLetter(lineofcode.charAt(i))) {
                    stack.push(lineofcode.charAt(i++));
                }
                String stackstr = stack.toString();
                stack.clear();
                stackstr = stackstr.replace(',', ' ').replace('[', ' ').replace(']', ' ').replaceAll("\\s", "");
                if (checkIfReserved(stackstr)) {
                    System.out.println(stackstr + " : Reserved Word");
                } else {
                    System.out.println(stackstr + " : Identifier");
                }

            } else if (checkIfDigit(lineofcode.charAt(i))) {
                while (checkIfDigit(lineofcode.charAt(i))) {
                    stack.push(lineofcode.charAt(i++));
                }
                String stackstr = stack.toString();
                stack.clear();
                stackstr = stackstr.replace(',', ' ').replace('[', ' ').replace(']', ' ').replaceAll("\\s", "");
                System.out.println(stackstr + " : Digit");

            } else if (checkIfAssign(lineofcode.charAt(i))) {
                if (lineofcode.charAt(++i) == '=') {
                    System.out.println(":= : Assignment");
                }

            } else if (checkIfSymbol(lineofcode.charAt(i))) {
                
                if(checkIfComment(lineofcode.charAt(i)))
                {
                while (lineofcode.charAt(i) != '}') {
                    stack.push(lineofcode.charAt(i++));
                }
                String stackstr = stack.toString();
                stack.clear();
                stackstr = stackstr.replace(',', ' ').replace('[', ' ').replace(']', ' ').replaceAll("\\s", "");
                System.out.println(stackstr + "} : Comment");
                }
                else
                System.out.println(lineofcode.charAt(i) + " : " + SymbolName(lineofcode.charAt(i)));

            }
            i++;

        }
        System.out.println("; : Semicolon");

    }

}