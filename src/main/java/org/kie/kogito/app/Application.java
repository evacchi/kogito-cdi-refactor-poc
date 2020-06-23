package org.kie.kogito.app;

import org.kie.kogito.Config;
import org.kie.kogito.uow.UnitOfWorkManager;

@javax.inject.Singleton()
public class Application implements org.kie.kogito.Application {

    @javax.inject.Inject()
    org.kie.kogito.Config config;

    @javax.inject.Inject()
    Processes processes;

    public Config config() {
        return config;
    }

    public UnitOfWorkManager unitOfWorkManager() {
        return config().process().unitOfWorkManager();
    }

    public Processes processes() {
        return processes;
    }
}
