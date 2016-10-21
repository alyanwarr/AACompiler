package aascanner;

import com.sun.org.apache.bcel.internal.generic.IFLE;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AAScanner {

    public static boolean checkIfReserved(String str) {
        if (str.equalsIgnoreCase("IF") || str.equalsIgnoreCase("THEN") || str.equalsIgnoreCase("ELSE") || str.equalsIgnoreCase("REPEAT") || str.equalsIgnoreCase("END") || str.equalsIgnoreCase("UNTIL") || str.equalsIgnoreCase("READ") || str.equalsIgnoreCase("WRITE")) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean checkIfComment(char cr) {
        if (cr == '{') {
            return true;
        } else {
            return false;
        }

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
            case ';':
                return "Semicolon";
            case '}':
                return "Left Braces Of The Previous Comment";
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
        if (cr == ';' || cr == '}' || cr == '{' || cr == '+' || cr == '-' || cr == '*' || cr == '/' || cr == '=' || cr == '<' || cr == '(' || cr == ')' || cr == ':' || cr == '=') {
            return true;
        } else {
            return false;
        }

    }

    public static void state1(char cr) {

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        AAScanner_GUI frame = new AAScanner_GUI();
        AAScannerGUI_OUT outgui = new AAScannerGUI_OUT();
        frame.setVisible(true);
        frame.jToggleButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String text = frame.jTextArea1.getText();
                try {
                    Writer fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Temp.txt")));
                    fw.write(text);
                    fw.close();
                    BufferedReader br2 = new BufferedReader(new FileReader("Temp.txt"));
                    Writer fw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Code.txt")));
                    String str;
                    while ((str = br2.readLine()) != null) {
                        fw2.append(str + " \n");

                    }
                    fw2.close();
                    Writer fw_tokens = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("tokens.txt")));

                    frame.jToggleButton1.setText("Scan Again");
                    outgui.setVisible(true);

                    int counter = 1;

                    BufferedReader br = new BufferedReader(new FileReader("Code.txt"));

                    String lineofcode;

                    Stack stack = new Stack();
                    while ((lineofcode = br.readLine()) != null) {

                        outgui.jTextArea1.setText(text);
                        int i = 0;
                        outgui.jTextArea2.setText(outgui.jTextArea2.getText() + "line " + counter + ": " + "\"" + lineofcode + "\"\n");
//            System.out.println("line " + counter + ": " + "\""+lineofcode+"\"");
                        while (i < lineofcode.length()) {

                            if (checkIfLetter(lineofcode.charAt(i))) {

                                while (checkIfLetter(lineofcode.charAt(i))) {
                                    stack.push(lineofcode.charAt(i));
                                    i++;
                                }

                                String stackstr = stack.toString();
                                stack.clear();
                                stackstr = stackstr.replace(',', ' ').replace('[', ' ').replace(']', ' ').replaceAll("\\s", "");

                                if (checkIfReserved(stackstr)) {
                                    outgui.jTextArea2.setText(outgui.jTextArea2.getText() + "\t " + stackstr + " : Reserved Word\n");
                                    fw_tokens.append(stackstr);
                                    fw_tokens.append("\n");
//                        System.out.println("\t " + stackstr + " : Reserved Word");
                                    continue;
                                } else {

                                    outgui.jTextArea2.setText(outgui.jTextArea2.getText() + "\t " + stackstr + " : Identifier\n");
//                        System.out.println("\t " + stackstr + " : Identifier");
                                    fw_tokens.append(stackstr);
                                    fw_tokens.append("\n");
                                    continue;
                                }

                            } else if (checkIfDigit(lineofcode.charAt(i))) {
                                while (checkIfDigit(lineofcode.charAt(i))) {
                                    stack.push(lineofcode.charAt(i));
                                    i++;
                                }
                                String stackstr = stack.toString();
                                stack.clear();
                                stackstr = stackstr.replace(',', ' ').replace('[', ' ').replace(']', ' ').replaceAll("\\s", "");

                                outgui.jTextArea2.setText(outgui.jTextArea2.getText() + "\t " + stackstr + ": Digit\n");
                                fw_tokens.append(stackstr);
                                fw_tokens.append("\n");
//                    System.out.println("\t " + stackstr + ": Digit");
                                continue;

                            } else if (checkIfSymbol(lineofcode.charAt(i))) {

                                if (lineofcode.charAt(i) == ':' && lineofcode.charAt(++i) == '=') {

                                    outgui.jTextArea2.setText(outgui.jTextArea2.getText() + "\t := : Assignment\n");
                                    fw_tokens.append(":=");
                                    fw_tokens.append("\n");
//                        System.out.println("\t := : Assignment");

                                } else if (checkIfComment(lineofcode.charAt(i))) {
                                    String ss = lineofcode;
                                    if (lineofcode.charAt(lineofcode.length() - 1) != '}') {
                                        int ii = lineofcode.length() - 2;
                                        ss = lineofcode.substring(0, ii + 1) + " } ";
                                    }

                                    while (ss.charAt(i) != '}') {

                                        stack.push(ss.charAt(i));
                                        i++;
                                    }
                                    String stackstr = stack.toString();
                                    stack.clear();
                                    stackstr = stackstr.replace(',', ' ').replace('[', ' ').replace(']', ' ').replaceAll("\\s", "");
                                    outgui.jTextArea2.setText(outgui.jTextArea2.getText() + "\t " + stackstr + "} : Comment\n");
//                        System.out.println("\t " + stackstr + "} : Comment");
                                } else if (checkIfSymbol(lineofcode.charAt(i))) {

                                    outgui.jTextArea2.setText(outgui.jTextArea2.getText() + "\t " + lineofcode.charAt(i) + " : " + SymbolName(lineofcode.charAt(i)) + "\n");
                                    if (lineofcode.charAt(i) != '}') {
                                        fw_tokens.append(lineofcode.charAt(i));
                                        fw_tokens.append("\n");
                                    }
//                        System.out.println("\t " + lineofcode.charAt(i) + " : " + SymbolName(lineofcode.charAt(i)));

                                }

                            }

                            i++;

//            System.out.println("--------------------------------");
                        }
                        counter++;
                    }
                    fw_tokens.close();
//        br.close();

                } catch (IOException ex) {
                    Logger.getLogger(AAScanner_GUI.class.getName()).log(Level.SEVERE, null, ex);

                } catch (StringIndexOutOfBoundsException ex2) {
                    Logger.getLogger(AAScanner_GUI.class.getName()).log(Level.SEVERE, null, ex2);

                }
                try {
                    AAParser Parser = new AAParser();
                } catch (IOException ex) {
                    Logger.getLogger(AAScanner.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NullPointerException ex3) {
                    System.out.println("shit");
                }

            }
        });
    }
}
