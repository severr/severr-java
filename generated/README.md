# swagger-java-client

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>io.swagger</groupId>
    <artifactId>swagger-java-client</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "io.swagger:swagger-java-client:1.0.0"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/swagger-java-client-1.0.0.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import severr.*;
import severr.auth.*;
import severr.model.*;
import io.swagger.client.api.EventsApi;

import java.io.File;
import java.util.*;

public class EventsApiExample {

    public static void main(String[] args) {
        
        EventsApi apiInstance = new EventsApi();
        AppEvent data = new AppEvent(); // AppEvent | Event to submit
        try {
            apiInstance.eventsPost(data);
        } catch (ApiException e) {
            System.err.println("Exception when calling EventsApi#eventsPost");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *https://www.severr.io/api/v1*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*EventsApi* | [**eventsPost**](docs/EventsApi.md#eventsPost) | **POST** /events | Submit an application event or error to Severr


## Documentation for Models

 - [AppEvent](docs/AppEvent.md)
 - [CustomData](docs/CustomData.md)
 - [CustomDoubleData](docs/CustomDoubleData.md)
 - [CustomStringData](docs/CustomStringData.md)
 - [Error](docs/Error.md)
 - [InnerStackTrace](docs/InnerStackTrace.md)
 - [StackTraceLine](docs/StackTraceLine.md)
 - [StackTraceLines](docs/StackTraceLines.md)
 - [Stacktrace](docs/Stacktrace.md)


## Documentation for Authorization

All endpoints do not require authorization.
Authentication schemes defined for the API:

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issue.

## Author


