package org.kie.kogito.app;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.kie.kogito.decision.DecisionConfig;
import org.kie.kogito.process.ProcessConfig;
import org.kie.kogito.rules.RuleConfig;

@javax.inject.Singleton()
public class ApplicationConfig implements org.kie.kogito.Config {

    @Inject
    protected Instance<ProcessConfig> processConfig;

    @Inject
    protected Instance<RuleConfig> ruleConfig;

    @Inject
    protected Instance<DecisionConfig> decisionConfig;

    @Override
    public ProcessConfig process() {
        return processConfig.get();
    }

    @Override
    public RuleConfig rule() {
        return ruleConfig.get();
    }

    @Override
    public DecisionConfig decision() {
        return decisionConfig.get();
    }
}
