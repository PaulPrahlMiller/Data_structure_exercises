package com.oop;

import java.util.*;

public class Compiler {

    static String cValid =
            """
                    int a;
                    int a; /* random comment */
                    int a; /* for storing width * height */
                    int a = b*c;
                    int a = b / c;
                    int a = 55; // This is a comment / [
                    public void doIt(int x) {System.out.println(x*100);}
                    int []arr = new int[10];
                    /* */ {}""";
    static String cInvalid =
            """
                    int [arr = new int[10];
                    int b = 5; /* this is a comment /
                    {a=b;
                    }""";
    static String cPlusValid =
            """
                    if(a == b) {a++;}
                    if(a < (b*c)) {t = 5;}
                    int []b = new int[5];
                    [](){}
                    int a = 5; // init a to 5""";
    static String cPlusInvalid =
            """
                    for(int i=0;i<10;i++] {a+= b;}
                    {abc)""";

    Map<String, String> symbols = Map.of(
            "(", ")",
            "{", "}",
            "[", "]",
            "/*", "*/"
    );

    ArrayList<String> commentTokens = new ArrayList<>(Arrays.asList(
            "//", "/*", "*/"));

    public boolean validate(String code){

        String[] tokens = code.split("");

        String[] stack = new String[tokens.length];
        int top = -1;

        String doubleToken;
        String currentToken;

        // Boolean values to check a token is an opening/closing symbol
        boolean isOpening;
        boolean isClosing;

        for(int i = 0; i < tokens.length; i++) {

            currentToken = tokens[i];

            if (i < tokens.length - 1){
                doubleToken = String.join("", tokens[i], tokens[i + 1]);
                if(commentTokens.contains(doubleToken)){
                    currentToken = doubleToken;
                }
            }

            // Boolean value for the bracket symbols. Comment symbols are handled separately.
            isClosing = symbols.containsValue(currentToken) && !commentTokens.contains(currentToken);
            isOpening = symbols.containsKey(currentToken) && !commentTokens.contains(currentToken);

            // Line Comment
            // Move the index position to the next line or to last token
            // in the string if no new line exists.
            if(currentToken.equals("//")){
                do{
                    i++;
                }
                while(!tokens[i].equals("\n") && i < tokens.length - 1);
            }

            if(currentToken.equals("/*")){

                // Add token to the stack
                stack[++top] = currentToken;

                // Move index pointer past comment token
                i+=2;

                // Move the index pointer until a closing comment token is found,
                // or to the last token if no closing comment has been found.
                while(!currentToken.equals("*/") && i < tokens.length - 1){
                    currentToken = String.join("", tokens[i], tokens[i+1]);
                    i++;
                }

                // Pop the stack when closing comment has been found.
                if(currentToken.equals("*/"))
                    top--;
            }

            // If closing symbol when stack is empty, code is invalid.
            if(isClosing && top < 0){
                System.out.printf("Error: Unexpected token '%s'%n", currentToken);
                return false;
            }

            // Add opening symbol to the stack
            if(isOpening){
                stack[++top] = currentToken;
            }

            // If closing symbol, Check if the symbol at the top of the stack
            // is the correct opening symbol.
            // Uses stack[top] as the key for the symbols Map
            //      --> returns the same symbol as currentToken if it is a match.
            // Otherwise the code is invalid.
            if(isClosing){
                if(symbols.get(stack[top]).equals(currentToken))
                    top--;
                else {
                    System.out.printf("Error: Unexpected token '%s'%n", currentToken);
                    return false;
                }
            }
        }

        // Stack pointer top will be -1 if the stack is empty. It should be empty to be valid.
          if(top > -1){
            // Gets the corresponding closing symbol to the opening symbol remaining in the stack.
            String expectedToken = symbols.get(stack[top]);
            System.out.printf("Error: '%s' expected%n", expectedToken);
            return false;
        }

        // When this point is reached the code has been validated.
        return true;
    }

    public static void main() {
        Compiler c = new Compiler();

        System.out.println("Valid C code");
        System.out.println("Validated: " + c.validate(cValid) + "\n");
        System.out.println("Invalid C code");
        System.out.println("Validated: " + c.validate(cInvalid) + "\n");
        System.out.println("Valid C++ code");
        System.out.println("Validated: " + c.validate(cPlusValid) + "\n");
        System.out.println("Invalid C++ code");
        System.out.println("Validated: " + c.validate(cPlusInvalid) + "\n");
    }
}
