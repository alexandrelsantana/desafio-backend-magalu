package com.magalu.application.use_cases.scheduled_message;

import com.magalu.application.use_cases.utils.scheduler.Scheduler;
import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGatewayInterface;
import com.magalu.domain.entity.scheduled_message.StatusScheduler;

import java.util.concurrent.ScheduledFuture;

public class CreateScheduledMessageTask extends Scheduler {
    private final ScheduledMessageGatewayInterface scheduledMessageGateway;

    public CreateScheduledMessageTask(ScheduledMessageGatewayInterface scheduledMessageGateway) {
        this.scheduledMessageGateway = scheduledMessageGateway;
    }

    /**
     * @param task
     * cria o scheduler em memória;
     * altera o status no banco de dados para 'Scheduled'
     */
    public ScheduledFuture<?> execute(
            ScheduledMessage entity,
            Runnable task){

        var future = super.createTask(entity.getScheduledTime(), task);

        updateStatus(entity);
        return future;
    }

    private void updateStatus(ScheduledMessage entity) {
        entity.changeStatus(StatusScheduler.SCHEDULED);
        scheduledMessageGateway.save(entity);
    }
}
