package org.kie.kogito.app;

import java.util.Optional;

import javax.inject.Singleton;

@Singleton
public class ProcessConfigProperties {
    @org.eclipse.microprofile.config.inject.ConfigProperty(name = "kogito.service.url")
    java.util.Optional<java.lang.String> serviceUrl;

    public Optional<String> serviceUrl() {
        return serviceUrl;
    }
}
