package com.epam.concurrency.task;

/**
 * Created by Tanmoy on 6/17/2016.
 */
public class Util {

    public static void sleep(){
        sleep(1000 * 2);
    }
    public static void sleep(long timeInMs){
        try{
            Thread.sleep(timeInMs);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
