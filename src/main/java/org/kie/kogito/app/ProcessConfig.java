package org.kie.kogito.app;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.kie.api.event.process.ProcessEventListener;
import org.kie.kogito.jobs.JobsService;
import org.kie.kogito.process.ProcessEventListenerConfig;
import org.kie.kogito.process.WorkItemHandlerConfig;
import org.kie.kogito.signal.SignalManagerHub;
import org.kie.kogito.uow.UnitOfWorkManager;
import org.kie.services.signal.DefaultSignalManagerHub;

@javax.inject.Singleton()
public class ProcessConfig implements org.kie.kogito.process.ProcessConfig {

    private final WorkItemHandlerConfig workItemHandlerConfig;
    private final ProcessEventListenerConfig processEventListenerConfig;
    private final UnitOfWorkManager unitOfWorkManager;
    private final JobsService jobsService;

    @Inject
    public ProcessConfig(
            Instance<org.kie.kogito.process.WorkItemHandlerConfig> workItemHandlerConfig,
            Instance<org.kie.kogito.uow.UnitOfWorkManager> unitOfWorkManager,
            Instance<org.kie.kogito.jobs.JobsService> jobsService,
            Instance<org.kie.kogito.process.ProcessEventListenerConfig> processEventListenerConfigs,
            Instance<org.kie.api.event.process.ProcessEventListener> processEventListeners,
            Instance<org.kie.kogito.event.EventPublisher> eventPublishers,
            ProcessConfigProperties cfg) {

        this.workItemHandlerConfig = extract_workItemHandlerConfig(workItemHandlerConfig);
        this.processEventListenerConfig = extract_processEventListenerConfig(processEventListenerConfigs, processEventListeners);
        this.unitOfWorkManager = extract_unitOfWorkManager(unitOfWorkManager);
        this.jobsService = extract_jobsService(jobsService);

        eventPublishers.forEach(publisher -> unitOfWorkManager().eventManager().addPublisher(publisher));
        unitOfWorkManager().eventManager().setService(cfg.serviceUrl().orElse(""));
    }

    @Override
    public WorkItemHandlerConfig workItemHandlers() {
        return workItemHandlerConfig;
    }

    @Override
    public ProcessEventListenerConfig processEventListeners() {
        return processEventListenerConfig;
    }

    @Override
    public SignalManagerHub signalManagerHub() {
        return signalManagerHub;
    }

    @Override
    public UnitOfWorkManager unitOfWorkManager() {
        return unitOfWorkManager;
    }

    @Override
    public JobsService jobsService() {
        return null;
    }

    private org.kie.kogito.process.WorkItemHandlerConfig defaultWorkItemHandlerConfig = new org.kie.kogito.process.impl.DefaultWorkItemHandlerConfig();

    private org.kie.kogito.uow.UnitOfWorkManager defaultUnitOfWorkManager = new org.kie.kogito.services.uow.DefaultUnitOfWorkManager(new org.kie.kogito.services.uow.CollectingUnitOfWorkFactory());

    private org.kie.kogito.jobs.JobsService defaultJobsService = null;

    private SignalManagerHub signalManagerHub = new DefaultSignalManagerHub();

    private static <C, L> List<L> merge(Collection<C> configs, Function<C, Collection<L>> configToListeners, Collection<L> listeners) {
        return Stream.concat(configs.stream().flatMap(c -> configToListeners.apply(c).stream()), listeners.stream()).collect(Collectors.toList());
    }

    protected org.kie.kogito.process.WorkItemHandlerConfig extract_workItemHandlerConfig(Instance<WorkItemHandlerConfig> workItemHandlerConfig) {
        if (workItemHandlerConfig.isUnsatisfied() == false) {
            return workItemHandlerConfig.get();
        } else {
            return defaultWorkItemHandlerConfig;
        }
    }

    protected org.kie.kogito.uow.UnitOfWorkManager extract_unitOfWorkManager(Instance<UnitOfWorkManager> unitOfWorkManager) {
        if (unitOfWorkManager.isUnsatisfied() == false) {
            return unitOfWorkManager.get();
        } else {
            return defaultUnitOfWorkManager;
        }
    }

    protected org.kie.kogito.jobs.JobsService extract_jobsService(Instance<JobsService> jobsService) {
        if (jobsService.isUnsatisfied() == false) {
            return jobsService.get();
        } else {
            return defaultJobsService;
        }
    }

    private org.kie.kogito.process.ProcessEventListenerConfig extract_processEventListenerConfig(Instance<ProcessEventListenerConfig> processEventListenerConfigs, Instance<ProcessEventListener> processEventListeners) {
        return this.merge_processEventListenerConfig(java.util.stream.StreamSupport.stream(processEventListenerConfigs.spliterator(), false).collect(java.util.stream.Collectors.toList()), java.util.stream.StreamSupport.stream(processEventListeners.spliterator(), false).collect(java.util.stream.Collectors.toList()));
    }

    private org.kie.kogito.process.ProcessEventListenerConfig merge_processEventListenerConfig(java.util.Collection<org.kie.kogito.process.ProcessEventListenerConfig> processEventListenerConfigs, java.util.Collection<org.kie.api.event.process.ProcessEventListener> processEventListeners) {
        return new org.kie.kogito.process.impl.CachedProcessEventListenerConfig(merge(processEventListenerConfigs, org.kie.kogito.process.ProcessEventListenerConfig::listeners, processEventListeners));
    }

    public org.kie.kogito.Addons addons() {
        return new org.kie.kogito.Addons(java.util.Arrays.asList());
    }
}
