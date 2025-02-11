package com.magalu.application.use_cases.scheduled_message;

import com.magalu.application.use_cases.utils.status_output.StatusFailed;
import com.magalu.application.use_cases.utils.status_output.StatusSuccess;
import com.magalu.application.use_cases.UseCase;
import com.magalu.domain.ValueObject.message.MessageGateway;
import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGateway;
import com.magalu.domain.entity.scheduled_message.status_scheduler.StatusSchedulerCancelled;
import com.magalu.domain.validation.Notification;

import java.util.Objects;

/**
 * Responsável por
 * - validar dados da mensagem;
 * - criar registro na base de dados da mensagem agendada; e
 * - agendar o envio da mensagem.
 */
public class ScheduledMessageUseCase extends UseCase<ScheduledMessageInput> {

    private final ScheduledMessageGateway scheduledMessageGateway;
    private final MessageGateway messageGateway;
    private Notification notification;

    public ScheduledMessageUseCase(
            final ScheduledMessageGateway scheduledMessageGateway,
            final MessageGateway messageGateway) {
        this.scheduledMessageGateway = Objects.requireNonNull(scheduledMessageGateway);
        this.messageGateway =  Objects.requireNonNull(messageGateway);
    }

    @Override
    public ScheduledMessageOutput execute(ScheduledMessageInput input) {
        this.notification = Notification.createNotification();

        final var scheduledMessage = ScheduledMessage.create(
                input.scheduledTime(),
                input.message(),
                input.to(),
                notification
        );

        hasError(() -> saveScheduledMessageInDatabase(scheduledMessage));
        hasError(() -> createScheduledMessage(scheduledMessage, () -> sendMessage(scheduledMessage)));

        return notification.hasError() ? createOutputFailed() : createOutputSuccess();
    }

    /**
     * verifica se o status  ainda é diferente de cancelado antes de executar a action.
     */
    private void hasError(Runnable action){
        if (!notification.hasError()){
            action.run();
        }
    }

    /**
     * Cria a mensagem em banco de dados no status "created".
     */
    private void saveScheduledMessageInDatabase(ScheduledMessage entity) {
        try{
            scheduledMessageGateway.save(entity);
        } catch (Exception e) {
            notification.append(
                    "Error when save scheduled in database",
                    e.getMessage()
            );
        }
    }

    /**
     * Cria o agendamento da tarefa no scheduler em mémoria. A Tarefa a ser executada é informada em "task".
     */
    private void createScheduledMessage(ScheduledMessage entity, Runnable task) {
        try{
            var createScheduledMessageTask = new CreateScheduledMessageTask(scheduledMessageGateway);
            createScheduledMessageTask.execute(entity, task);
        } catch (Exception e) {
            notification.append(
                    "Error when create new scheduled task",
                    e.getMessage()
            );
        }
    }

    /**
     * Tarefa a ser executada pelo scheduler.
     * Verifica se o status  ainda é diferente de cancelado antes de executar o envio da mensagem.
     */
    private void sendMessage(ScheduledMessage entity) {
        var currentStatus = scheduledMessageGateway.findById(entity.getUuid()).getStatusScheduler();
        if (currentStatus instanceof StatusSchedulerCancelled){
            return;
        }

        try{
            messageGateway.send(entity.getMessage());

        } catch (Exception e) {
            notification.append(
                    "Error when send message",
                    e.getMessage()
            );
        }
    }

    private ScheduledMessageOutput createOutputFailed(){
        return ScheduledMessageOutput.createScheduledMessageOutput(
                StatusFailed.create(),
                notification
        );
    }

    private ScheduledMessageOutput createOutputSuccess() {
        return ScheduledMessageOutput.createScheduledMessageOutput(
                StatusSuccess.create(),
                notification
        );
    }
}
