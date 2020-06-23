package org.kie.kogito.app;

import javax.inject.Singleton;
import javax.ws.rs.Produces;

import org.acme.travels.generated.ScriptsProcess;

// GENERATED AND INTERNAL
@Singleton
@org.kie.internal.kogito.codegen.Generated(value = "kogito-codegen", reference = "global", name = "Global")
public class ProcessProducer {

    @javax.enterprise.context.ApplicationScoped()
    @javax.inject.Named("scripts")
    @Produces
    public ScriptsProcess createScriptsProcess(Application app) {
        return new ScriptsProcess(app);
    }


}
