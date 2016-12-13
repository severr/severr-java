package io.severr.client;

import io.severr.model.AppEvent;
import org.apache.log4j.Logger;
import severr.ApiCallback;
import severr.ApiException;
import severr.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class SampleSeverrApp {
    private static Logger logger = Logger.getLogger(SampleSeverrApp.class.getName());

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // Option-1: Use log4j
        //logger.error("This is a test log4j exception.", new Exception("Test log4j exception."));

        ExecutorService pool = Executors.newFixedThreadPool(20);
        ///*
        // Option-2: Send an event manually.
        List<Future<Object>> submits = new ArrayList<Future<Object>>();

        for(int j = 0; j < 20; j++) {
            final int finalJ = j;
            Future<Object> submit = pool.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    SeverrClient client = new SeverrClient("5253581baed17315b0e41d62939f924511503356201105", "http://ec2-52-91-176-104.compute-1.amazonaws.com/api/v1", null, null, null, null, null, null, null, null);

                    // Option-3: Throw exception.

                    try {
                        throw new Exception("This is a test exception.");
                    } catch (Exception e) {
                        for (int i = 0; i < 10; i++) {
                            final long t1 = System.nanoTime();
                            client.sendException("Error data " + (i * finalJ), e);
                            final long t2 = System.nanoTime();
                            System.out.println("Sent[" + finalJ + "]: " + (i * finalJ) + " time (in ms): " + (t2-t1)/1000000);
                        }
                    }//*/
                    return null;
                }
            });

            submits.add(submit);
        }

        for (Future<Object> submit: submits) {
            submit.get();
        }
    }
}
