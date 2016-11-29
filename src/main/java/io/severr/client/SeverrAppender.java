package io.severr.client;

import io.severr.model.AppEvent;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import severr.ApiCallback;
import severr.ApiException;

import java.util.List;
import java.util.Map;

public class SeverrAppender extends AppenderSkeleton {

    private boolean enabled;
    private String apiKey;
    private String url;
    private String env;
    private String appVersion;
    private String dataCenter;
    private String dataCenterRegion;
    private boolean useAsync;
    private SeverrClient severrClient;

    @Override
    public void activateOptions() {
        if (!this.enabled) return;
        this.severrClient = new SeverrClient(this.apiKey, this.url, this.appVersion, this.env, null, null, null, null, this.dataCenter, this.dataCenterRegion);
    }

    @Override
    protected void append(LoggingEvent loggingEvent) {
        if (!this.enabled) return;

        // get classification in propercase (first letter capitalized)
        String classification = loggingEvent.getLevel().toString().toLowerCase();
        classification = classification.substring(0, 1).toUpperCase() + classification.substring(1);

        // get event type
        ThrowableInformation throwableInformation = loggingEvent.getThrowableInformation();
        Throwable throwable = throwableInformation == null ? null : throwableInformation.getThrowable();
        String eventType = throwable == null ? loggingEvent.getLoggerName() : throwable.getClass().getName();

        // create app event
        AppEvent event = this.severrClient.createAppEvent(classification, eventType, loggingEvent.getRenderedMessage());

        // build the stack trace
        event.setEventStacktrace(EventTraceBuilder.getEventTraces(throwable));

        // send the event
        try {
            if(this.useAsync) {
                this.severrClient.sendEventAsync(event, new ApiCallback<Void>() {
                    @Override
                    public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                        // do nothing
                    }

                    @Override
                    public void onSuccess(Void result, int statusCode, Map<String, List<String>> responseHeaders) {
                        // do nothing
                    }

                    @Override
                    public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
                        // do nothing
                    }

                    @Override
                    public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
                        // do nothing
                    }
                });
            } else {
                this.severrClient.sendEvent(event);
            }
        } catch (ApiException ignored) {
            // ignored
        }
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public void setDataCenter(String dataCenter) {
        this.dataCenter = dataCenter;
    }

    public void setDataCenterRegion(String dataCenterRegion) {
        this.dataCenterRegion = dataCenterRegion;
    }

    public void setUseAsync(boolean useAsync) {
        this.useAsync = useAsync;
    }
}
