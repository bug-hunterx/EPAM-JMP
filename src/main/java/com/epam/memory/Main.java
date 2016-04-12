package com.epam.memory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Tkachi on 2016/4/7.
 */
public class Main {

    public static void main(String[] args) {
        Random random = new Random();
        List<String> interned = new LinkedList<>();
        while(true){
            int lenght = random.nextInt(100);
            StringBuilder builder = new StringBuilder();
            String chars = "qwertyuiopasdfghjklzxcvbnm";
            for(int i=0;i<lenght;i++){
                builder.append(chars.charAt(random.nextInt(chars.length())));
            }
            interned.add(builder.toString().intern());
        }
    }
}
