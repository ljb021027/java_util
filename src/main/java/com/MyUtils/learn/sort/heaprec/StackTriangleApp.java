package com.MyUtils.learn.sort.heaprec;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author ljb
 * @since 2018/12/30
 */
public class StackTriangleApp {
    static int theNumber;
    static int theAnswer;
    static StackX theStackX;
    static int codePart;
    static Params theseParams;

    public static void main(String[] args) throws NumberFormatException, IOException {
        System.out.print("Enter a number: ");
        theNumber = getInt();
        recTriangle();
        System.out.println("Triangle= " + theAnswer);

    }

    private static void recTriangle() {
        // TODO Auto-generated method stub
        theStackX = new StackX(1000);
        codePart = 1;
        while (step() == false) {
            ;
        }
    }

    private static boolean step() {
        switch (codePart) {
            case 1:
                theseParams = new Params(theNumber, 6);
                theStackX.Push(theseParams);
                codePart = 2;
                break;

            case 2:
                theseParams = theStackX.peek();
                if (theseParams.n == 1) {
                    theAnswer = 1;
                    codePart = 5;
                }else {
                    codePart = 3;
                }
                break;

            case 3:
                Params newParams = new Params(theseParams.n - 1, 4);
                theStackX.Push(newParams);
                codePart = 2;
                break;

            case 4:
                theseParams = theStackX.peek();
                theAnswer = theAnswer + theseParams.n;
                codePart = 5;
                break;

            case 5:
                theseParams = theStackX.peek();
                codePart = theseParams.returnAddress;
                theStackX.Pop();
                break;

            case 6:
                return true;
            default:

                break;
        }
        return false;
    }

    private static int getInt() throws NumberFormatException, IOException {
        int a = Integer.parseInt(getString());
        return a;
    }

    private static String getString() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader buf = new BufferedReader(inputStreamReader);
        String str = buf.readLine();
        return str;
    }

}

