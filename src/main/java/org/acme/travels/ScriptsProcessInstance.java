package org.acme.travels;

public class ScriptsProcessInstance extends org.kie.kogito.process.impl.AbstractProcessInstance<ScriptsModel> {

    public ScriptsProcessInstance(org.acme.travels.ScriptsProcess process, ScriptsModel value, org.kie.api.runtime.process.ProcessRuntime processRuntime) {
        super(process, value, processRuntime);
    }

    public ScriptsProcessInstance(org.acme.travels.ScriptsProcess process, ScriptsModel value, java.lang.String businessKey, org.kie.api.runtime.process.ProcessRuntime processRuntime) {
        super(process, value, businessKey, processRuntime);
    }

    protected java.util.Map<String, Object> bind(ScriptsModel variables) {
        return variables.toMap();
    }

    protected void unbind(ScriptsModel variables, java.util.Map<String, Object> vmap) {
        variables.fromMap(this.id(), vmap);
    }
}
