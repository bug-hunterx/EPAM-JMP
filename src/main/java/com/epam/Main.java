package com.epam;

import java.io.IOException;

/**
 * Created by Tkachi on 2016/4/3.
 */
public class Main {

    private static final String ACCIDENTS_CSV = "src/main/resources/DfTRoadSafety_Accidents_2009.csv";

    public volatile static boolean flag = true;


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
