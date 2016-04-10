package com.epam.demo.collections;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Tkachi on 2016/4/4.
 */
public class StreamingDemo {

    public static void main(String[] args) {
//        firstStreamingDemo();
        thinkAboutOrderInStreams_Good();
    }

    private static void firstStreamingDemo() {
        IntStream.range(0, 10)
                .filter(value -> value%2 == 0)
                .forEach(System.out::println);
    }

    private static void streamsAreLazy(){
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .forEach(s -> System.out.println("forEach: " + s));
    }

    private static void thinkAboutOrderInStreams_Bad(){
        Stream.of("d2", "a2", "b1", "b3", "c")
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));
    }

    private static void thinkAboutOrderInStreams_Good(){
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));
    }


}
