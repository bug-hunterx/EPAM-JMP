package com.epam.memory;

/**
 * Created by Tkachi on 2016/4/10.
 */
public class FindMaxStackLenght {

    private static int callDepth = 0;

    public static void main(String[] args) {
        FindMaxStackLenght findMaxStackLenght = new FindMaxStackLenght();
        findMaxStackLenght.recursiveCall();
    }

    private void recursiveCall(){
        callDepth += 1;
        if(callDepth%1000 == 0){
            System.out.println("Call dapth is " + callDepth);
        }
        recursiveCall();
    }
}
