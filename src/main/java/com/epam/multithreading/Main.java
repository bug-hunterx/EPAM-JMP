package com.epam.multithreading;

import java.io.IOException;

/**
 * Created by Tkachi on 2016/4/3.
 */
public class Main {

//try to run with volatile and without
    public static boolean flag = true;


    public static void main(String[] args) throws IOException {
        new Thread(() -> {
            int counter = 0;
            while(flag){
                counter++;
            }
            System.out.println(counter);
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = false;
        }).start();



    }

}
