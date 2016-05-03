package com.epam.service;

import com.epam.service.MockPoliceForceService;
import org.junit.Test;

import java.util.concurrent.*;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Created by Alexey on 03.05.2016.
 */
public class MockPoliceForceServiceTest {
    private MockPoliceForceService underTest;

    @Test
    public void should_return_nonempty_contact() throws Exception {
        underTest = new MockPoliceForceService(1);
        assertThat(underTest.getContactNo(""), not(equalTo("")));
    }

    @Test
    public void should_return_empty_when_interrupted() throws ExecutionException, InterruptedException {
        underTest = new MockPoliceForceService(1000);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        FutureTask<String> getNumberTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return underTest.getContactNo("");
            }
        });
        executor.submit(getNumberTask);
        executor.shutdownNow();

        assertThat("Should return empty upon interruption",
                getNumberTask.get(), equalTo(""));
    }
}
