package aascanner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.bind.annotation.XmlElement;

public class AAParser {

    static String currentToken;
    static BufferedReader br;

    public AAParser() throws IOException {
        br = new BufferedReader(new FileReader("tokens.txt"));
        readToken(br);
        program();
    }

    public static void readToken(BufferedReader br) throws IOException {

        currentToken = br.readLine();
        if (!currentToken.equals(null)) {//System.out.println("Current Token --> " + currentToken);
            //    AAScannerGUI_OUT.jTextArea3.setText(AAScannerGUI_OUT.jTextArea3.getText()+"Current Token --> " + currentToken +'\n');
        }
    }

    public static void ERROR() {
        System.out.println("EROOR FOUND");
    }

    public static void match(String expected) throws IOException {
        System.out.println(currentToken + " expected --> " + expected);
        //AAScannerGUI_OUT.jTextArea3.setText(AAScannerGUI_OUT.jTextArea3.getText()+currentToken + " expected --> " + expected +'\n');

        if (currentToken.equalsIgnoreCase(expected)) {
            System.out.println("TOKEN MATCHED");
          //  AAScannerGUI_OUT.jTextArea3.setText(AAScannerGUI_OUT.jTextArea3.getText()+"TOKEN MATCHED\n" );

            if (currentToken.equals("end")) {
                AAScannerGUI_OUT.jTextArea3.setText(AAScannerGUI_OUT.jTextArea3.getText() + "IF-STATEMENT Found\n" + "-----------------------------------------------------------------------------------------------\n");
            }
            readToken(br);
        } else {
            ERROR();
        }

    }
    //===============================================================

    public static void program() throws IOException {
        stmt_seq();
        AAScannerGUI_OUT.jTextArea3.setText(AAScannerGUI_OUT.jTextArea3.getText() + "Program Found\n");
        System.out.println("PROGRAM FOUND");

    }

    public static void stmt_seq() throws IOException {
        stmt();
        try {
            while (currentToken.equals(";")) {
                match(";");
                stmt();

            }
        } catch (Exception nullException) {
        }

        System.out.println("stmt-seq FOUND");
        //  AAScannerGUI_OUT.jTextArea3.setText(AAScannerGUI_OUT.jTextArea3.getText()+"stmt-seq FOUND\n");

    }

    public static void stmt() throws IOException {

        switch (currentToken) {
            case "if":
                if_stmt();
                break;

            case "repeat":
                repeat_stmt();
                break;
            case "read":
                read_stmt();
                break;
            case "write":
                write_stmt();
                break;
            default:

                assign_stmt();

                break;

        }
        System.out.println("statement found");
        //    AAScannerGUI_OUT.jTextArea3.setText(AAScannerGUI_OUT.jTextArea3.getText()+"Statement Found\n");
    }

    public static void if_stmt() throws IOException {

        match("if");
        exp();
        match("then");
        stmt_seq();

        if (currentToken.equalsIgnoreCase("else")) {
            match("else");
            stmt_seq();
        }

        match("end");
        AAScannerGUI_OUT.jTextArea3.setText(AAScannerGUI_OUT.jTextArea3.getText() + "IF Statement Found\n");

    }

    public static void assign_stmt() throws IOException {
        match(currentToken);
        match(":=");
        exp();
        System.out.println("ASSIGNMENT STATEMENT FOUND");
        AAScannerGUI_OUT.jTextArea3.setText(AAScannerGUI_OUT.jTextArea3.getText() + "ASSIGNMENT Statement Found\n" + "-----------------------------------------------------------------------------------------------\n");

    }

    public static void repeat_stmt() throws IOException {

        match("repeat");
        stmt_seq();
        match("until");
        exp();
        System.out.println("REPEAT STATEMENT FOUND");
        AAScannerGUI_OUT.jTextArea3.setText(AAScannerGUI_OUT.jTextArea3.getText() + "REPEAT Statement Found\n" + "-----------------------------------------------------------------------------------------------\n");

    }

    public static void write_stmt() throws IOException {

        match("write");
        exp();
        System.out.println("WRITE STATEMENT FOUND");
        AAScannerGUI_OUT.jTextArea3.setText(AAScannerGUI_OUT.jTextArea3.getText() + "WRITE Statement Found\n" + "-----------------------------------------------------------------------------------------------\n");

    }

    public static void read_stmt() throws IOException {
        match("read");
        match(currentToken);
        System.out.println("READ STATEMENT FOUND");
        AAScannerGUI_OUT.jTextArea3.setText(AAScannerGUI_OUT.jTextArea3.getText() + "Read Statement Found\n" + "-----------------------------------------------------------------------------------------------\n");

    }

    public static void exp() throws IOException {
        simple_exp();
        if (currentToken.equals("<")) {
            match("<");
            simple_exp();

        } else if (currentToken.equals("=")) {
            match("=");
            simple_exp();

        }
        System.out.println("exp FOUND");
        //  AAScannerGUI_OUT.jTextArea3.setText(AAScannerGUI_OUT.jTextArea3.getText()+"exp Found\n");

    }

    public static void simple_exp() throws IOException {
        term();
        while (currentToken.equals("-") || currentToken.equals("+")) {
            if (currentToken.equals("-")) {
                match("-");
            } else {
                match("+");
            }
            term();

        }
        System.out.println("simple-exp FOUND");
        //  AAScannerGUI_OUT.jTextArea3.setText(AAScannerGUI_OUT.jTextArea3.getText()+"simple-exp FOUND\n");

    }

    public static void term() throws IOException {
        factor();
        while (currentToken.equals("*") || currentToken.equals("/")) {
            if (currentToken.equals("*")) {
                match("*");
            } else {
                match("/");
            }
            factor();
        }
        System.out.println("term  FOUND");
        // AAScannerGUI_OUT.jTextArea3.setText(AAScannerGUI_OUT.jTextArea3.getText()+"term FOUND\n");

    }

    public static void factor() throws IOException {
        if (currentToken.equals("(")) {
            match("(");
            exp();
            match(")");
        } else if (Character.isDigit(currentToken.charAt(0))) {
            match(currentToken);
        } else if (Character.isAlphabetic(currentToken.charAt(0))) {
            match(currentToken);
        }
        System.out.println("factor FOUND");
        //   AAScannerGUI_OUT.jTextArea3.setText(AAScannerGUI_OUT.jTextArea3.getText()+"factor FOUND  \n");

    }
}
