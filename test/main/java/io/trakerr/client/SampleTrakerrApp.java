package io.trakerr.client;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SampleTrakerrApp {
    private static Logger logger = Logger.getLogger(SampleTrakerrApp.class.getName());

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        TrakerrClient client = new TrakerrClient("<your api key>", "1.0", "development", "1.0");

        // Option-1: Use log4j
        logger.error("This is a test log4j exception.", new Exception("Test log4j exception."));

        /*
        // Option-2: Throw exception.
        try {
            throw new Exception("This is a test exception.");
        } catch(Exception e) {
            client.sendException("Error", e);
        }

        // Option-3: Send an event (including non-exceptions) manually.
        TrakerrClient client = new TrakerrClient("<your api key>", "1.0", "development", "1.0");
        AppEvent event = client.createAppEvent("Error", "System.Exception", "Some message");
        try {
            ApiResponse<Void> response = client.sendEvent(event);

            System.out.println("Sent event: " + response.getStatusCode() + ", data: " + response.toString());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        */
    }
}
