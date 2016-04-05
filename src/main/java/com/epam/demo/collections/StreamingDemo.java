package com.epam.demo.collections;

import java.util.stream.IntStream;

/**
 * Created by Tkachi on 2016/4/4.
 */
public class StreamingDemo {

    public static void main(String[] args) {
        firstStreamingDemo();
    }

    private static void firstStreamingDemo() {
        IntStream.range(0, 10)
                .filter(value -> value%2 == 0)
                .forEach(System.out::println);
    }


}
