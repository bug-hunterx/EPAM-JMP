package com.epam.memory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Tkachi on 2016/4/7.
 */
public class InternedStringsDemo {

    public static void main(String[] args) {
        Random random = new Random();
        List<String> interned = new LinkedList<String>();

        while (true) {
            int lenght = random.nextInt(200);
            String chars = "qwertyuiopasdfghjklzxcvbnm";
            StringBuilder builder = new StringBuilder();

            builder.append(chars.charAt(random.nextInt(chars.length())));
            interned.add(builder.toString().intern());
        }
    }
}
