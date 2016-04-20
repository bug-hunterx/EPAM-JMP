package com.epam.memory;

import java.util.LinkedList;

/**
 * Created by Tkachi on 2016/4/12.
 */
public class HeapOutOfMemory {

    public static void main(String[] args) throws InterruptedException {
        LinkedList<Long> list = new LinkedList<Long>();
        Thread.sleep(10000);

        for(long l=0;l<Long.MAX_VALUE;l++){
            list.add(l);
            for(int i=0;i<5000;i++){
                if(i==5000) break;
            }
        }
    }
}
