package com.epam.memory;

import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Created by Tkachi on 2016/4/12.
 */
public class GCDemo {

    public static void main(String[] args) throws InterruptedException {
        LinkedList<Long> list = new LinkedList<>();
        Thread.sleep(10000);

        long maxLength = 1000000l;

        for(long l=0;l<Long.MAX_VALUE;l++){
            list.add(l);
            for(int i=0;i<5000;i++){
                if(i==5000) break;
            }

            if(list.size()>maxLength){
                list = list.parallelStream().filter(aLong -> aLong<maxLength/2l).collect(Collectors.toCollection(LinkedList::new));
            }
        }


    }
}
