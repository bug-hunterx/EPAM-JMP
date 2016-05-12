package com.epam;

import com.epam.processor.MultithreadedProcessor;

import java.io.IOException;

/**
 * Created by Tkachi on 2016/4/3.
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        MultithreadedProcessor.process();

    }
}
