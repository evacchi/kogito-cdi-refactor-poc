package org.kie.kogito.app;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.kie.kogito.Model;
import org.kie.kogito.process.Process;

@javax.enterprise.context.ApplicationScoped()
public class Processes implements org.kie.kogito.process.Processes {

    @javax.inject.Inject()
    Application app;

    @javax.inject.Inject()
    javax.enterprise.inject.Instance<org.kie.kogito.process.Process<? extends org.kie.kogito.Model>> processes;

    private Map<String, Process<? extends Model>> mappedProcesses = new HashMap<>();

    @javax.annotation.PostConstruct
    public void setup() {
        for (Process<? extends Model> process : processes) {
            mappedProcesses.put(process.id(), process);
        }
    }

    public Process<? extends Model> processById(String processId) {
        return mappedProcesses.get(processId);
    }

    public Collection<String> processIds() {
        return mappedProcesses.keySet();
    }
}
