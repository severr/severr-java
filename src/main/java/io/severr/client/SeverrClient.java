package io.severr.client;

import com.squareup.okhttp.Call;
import io.severr.EventsApi;
import io.severr.model.AppEvent;
import severr.ApiCallback;
import severr.ApiClient;
import severr.ApiException;
import severr.ApiResponse;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * Client to create and send events to Severr.
 **/
public class SeverrClient {
    private String apiKey;
    private String contextAppVersion;
    private String contextEnvName;
    private String contextEnvVersion;
    private String contextEnvHostname;
    private String contextAppOS;
    private String contextAppOSVersion;
    private String contextDataCenter;
    private String contextDataCenterRegion;
    private EventsApi eventsApi;

    /**
     * Initialize a new instance of SeverrClient with specified options. If null is passed to any of the optional parameters, the defaults are used.
     *
     * @param apiKey                  (required) Specify the API key for this application
     * @param url                     (optional) URL to the Severr host, pass null to use default
     * @param contextAppVersion       (optional) application version, defaults to 1.0
     * @param contextEnvName          (optional) environment name like "development", "staging", "production", defaults to "development"
     * @param contextEnvVersion       (optional) environment version
     * @param contextEnvHostname      (optional) hostname of the environment
     * @param contextAppOS            (optional) operating system
     * @param contextAppOSVersion     (optional) operating system version
     * @param contextDataCenter       (optional) data center
     * @param contextDataCenterRegion (optional) data center region
     */
    public SeverrClient(String apiKey, String url, String contextAppVersion, String contextEnvName, String contextEnvVersion, String contextEnvHostname, String contextAppOS, String contextAppOSVersion, String contextDataCenter, String contextDataCenterRegion) {
        this.apiKey = apiKey;
        this.contextAppVersion = contextAppVersion == null ? "1.0" : contextAppVersion;
        this.contextEnvName = contextEnvName == null ? "development" : contextEnvName;
        this.contextEnvVersion = contextEnvVersion;
        try {
            this.contextEnvHostname = contextEnvHostname == null ? InetAddress.getLocalHost().getHostName() : contextEnvHostname;
        } catch (UnknownHostException ignored) {
        }
        this.contextAppOS = contextAppOS == null ? System.getProperty("os.name") : contextAppOS;
        this.contextAppOSVersion = contextAppOSVersion == null ? System.getProperty("os.version") : contextAppOSVersion;
        this.contextDataCenter = contextDataCenter;
        this.contextDataCenterRegion = contextDataCenterRegion;

        ApiClient client = new ApiClient();
        if (url != null) {
            client.setBasePath(url);
        }

        this.eventsApi = new EventsApi(client);
    }

    /**
     * Use this to bootstrap a new AppEvent object with the supplied classification, event type and message.
     *
     * @param classification Classification (Error/Warning/Info/Debug or custom string), defaults to "Error".
     * @param eventType      Type of event (eg. System.Exception), defaults to "unknonwn"
     * @param eventMessage   Message, defaults to "unknown"
     * @return Newly created AppEvent
     */
    public AppEvent createAppEvent(String classification, String eventType, String eventMessage) {
        if (classification == null) classification = "Error";
        if (eventType == null) eventType = "unknown";
        if (eventMessage == null) eventMessage = "unknown";

        AppEvent event = new AppEvent();
        event.setClassification(classification);
        event.setEventType(eventType);
        event.setEventMessage(eventMessage);

        return FillDefaults(event);
    }

    /**
     * Send exception to Severr by creating a new AppEvent and populating the stack trace.
     *
     * @param classification Classification like Error/Warn/Info/Debug
     * @param e exception
     * @throws RuntimeException when an error occurs sending the exception
     */
    public void sendException(String classification, Throwable e) {
        AppEvent event = createAppEvent(classification, e.getClass().getName(), e.getMessage());
        event.setEventStacktrace(EventTraceBuilder.getEventTraces(e));;
        try {
            sendEvent(event);
        } catch (ApiException apiException) {
            throw new RuntimeException(apiException.getMessage(), apiException);
        }
    }

    /**
     * Send the AppEvent to Severr. If any of the parameters supplied in the constructor are not present, this will auto-populate those members on the supplied event before sending the event to Severr.
     *
     * @param appEvent The event to send
     */
    public ApiResponse<Void> sendEvent(AppEvent appEvent) throws ApiException {
        // fill defaults if not overridden in the AppEvent being passed
        FillDefaults(appEvent);

        return this.eventsApi.eventsPostWithHttpInfo(appEvent);
    }

    /**
     * Send the AppEvent to Severr. If any of the parameters supplied in the constructor are not present, this will auto-populate those members on the supplied event before sending the event to Severr.
     *
     * @param appEvent The event to send
     */
    public Call sendEventAsync(AppEvent appEvent, ApiCallback<Void> callback) throws ApiException {
        // fill defaults if not overridden in the appEvent being passed
        FillDefaults(appEvent);

        return eventsApi.eventsPostAsync(appEvent, callback);
    }

    private AppEvent FillDefaults(AppEvent appEvent) {
        if (appEvent.getApiKey() == null) appEvent.setApiKey(this.apiKey);
        ;

        if (appEvent.getContextAppVersion() == null) appEvent.setContextAppVersion(this.contextAppVersion);

        if (appEvent.getContextEnvName() == null) appEvent.setContextEnvName(this.contextEnvName);
        if (appEvent.getContextEnvVersion() == null) appEvent.setContextEnvVersion(this.contextEnvVersion);
        if (appEvent.getContextEnvHostname() == null) appEvent.setContextEnvHostname(this.contextEnvHostname);

        if (appEvent.getContextAppOS() == null) {
            appEvent.setContextAppOS(this.contextAppOS);
            appEvent.setContextAppOSVersion(this.contextAppOSVersion);
        }

        if (appEvent.getContextDataCenter() == null) appEvent.setContextDataCenter(contextDataCenter);
        if (appEvent.getContextDataCenterRegion() == null) appEvent.setContextDataCenterRegion(contextDataCenterRegion);

        if (appEvent.getEventTime() == null) appEvent.setEventTime(System.currentTimeMillis());

        return appEvent;
    }


}