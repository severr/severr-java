package io.severr.client;

import io.severr.model.AppEvent;
import org.apache.log4j.Logger;
import severr.ApiException;
import severr.ApiResponse;

public class SampleSeverrApp {
    private static Logger logger = Logger.getLogger(SampleSeverrApp.class.getName());

    public static void main(String[] args) {

        // Option-1: Use log4j
        logger.error("This is a test log4j exception.", new Exception("Test log4j exception."));

        /*
        // Option-2: Send an event manually.
        SeverrClient client = new SeverrClient("62d0e191408b397262a7748c10579f5f38661405883", null, null, null, null, null, null, null, null, null);
        AppEvent event = client.createAppEvent("Error", "foo", "bar");
        try {
            ApiResponse<Void> response = client.sendEvent(event);

            System.out.println("Sent event: " + response.getStatusCode() + ", data: " + response.toString());
        } catch (ApiException e) {
            e.printStackTrace();
        }

        // Option-3: Throw exception.
        try {
            throw new Exception("This is a test exception.");
        } catch(Exception e) {
            client.sendException("Error", e);
        }*/
    }
}
