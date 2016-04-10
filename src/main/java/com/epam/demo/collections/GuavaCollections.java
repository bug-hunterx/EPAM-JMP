package com.epam.demo.collections;

import com.google.common.collect.*;

import java.util.List;

/**
 * Created by Tkachi on 2016/4/4.
 */
public class GuavaCollections {

    public static void main(String[] args) {
//        multiMapDemo();
//        biMapDemo();
        immutableDemo();
    }

    public static void multiMapDemo(){
        ListMultimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("Bogdan", "Feng Chen");
        multimap.put("Bogdan", "Mykola Turunov");
        multimap.put("Gary", "Teacher Cheng");
        multimap.put("Gary", "Gavin Li");

        System.out.println(multimap);
        System.out.println("Mentees: " + multimap.values());
        System.out.println("Mentors: " + multimap.keys());
    }

    public static void biMapDemo(){
        BiMap<String, String> menteesByMentors= HashBiMap.create();

        menteesByMentors.put("Bogdan", "Feng Chen");
        menteesByMentors.put("Key", "Feng Chen");
        menteesByMentors.put("Gary", "Teacher Cheng");
        BiMap<String, String> mentorsbyMentees = menteesByMentors.inverse();

        System.out.println(menteesByMentors);
        System.out.println(mentorsbyMentees);

    }

    public static void immutableDemo(){
        List<String> list = ImmutableList.of("test", "test2");
        list.add("test2");
    }
}
