package com.epam.dataservice;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class PoliceForceServiceTest {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        FutureTask<String> task = new FutureTask<String>(new MyCallable());
        executor.execute(new MyRunnable());
        //executor.execute(task);
        task.isDone();
        try {
			task.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("task done");
        System.out.println("waiting");

    }

    private static class MyCallable implements Callable<String>{

        @Override
        public String call() throws Exception {
            return "hi " + Thread.currentThread().getName();
        }
    }

    private static class MyRunnable implements Runnable{

        @Override
        public void run(){

            System.out.println("hi " + Thread.currentThread().getName());
        }
    }
}

