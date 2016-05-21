package com.epam.extservices;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Created by Alexey on 03.05.2016.
 */
public class DummyPoliceForceServiceTest {
    private static final String EMPTY_STRING = "";

    private DummyPoliceForceService underTest;

    @Test
    public void should_return_nonempty_contact() throws Exception {
        underTest = new DummyPoliceForceService(1);
        assertThat(underTest.getContactNo(EMPTY_STRING), not(equalTo(EMPTY_STRING)));
    }

    @Test
    public void should_return_empty_when_interrupted() throws ExecutionException, InterruptedException {
        underTest = new DummyPoliceForceService(1000);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        FutureTask<String> getNumberTask = new FutureTask<String>(() -> underTest.getContactNo(EMPTY_STRING));
        executor.submit(getNumberTask);
        executor.shutdownNow();

        assertThat("Should return empty upon interruption",
                getNumberTask.get(), equalTo(EMPTY_STRING));
    }
}
