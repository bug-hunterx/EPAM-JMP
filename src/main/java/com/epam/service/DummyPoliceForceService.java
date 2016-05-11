package com.epam.service;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alexey on 25.04.2016.
 */
public class DummyPoliceForceService implements PoliceForceService {
    private final long maxExecutionTime;

    public DummyPoliceForceService(long maxExecutionTime) {
        this.maxExecutionTime = maxExecutionTime;
    }

    @Override
    public String getContactNo(String forceName) throws Exception {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(maxExecutionTime));
        } catch (InterruptedException e) {
            return "";
        }
        return "Phone number";
    }
}
