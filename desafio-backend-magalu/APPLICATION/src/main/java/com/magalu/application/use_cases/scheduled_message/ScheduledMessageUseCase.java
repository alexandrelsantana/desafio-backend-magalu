package com.magalu.application.use_cases.scheduled_message;

import com.magalu.application.use_cases.utils.output.StatusFailed;
import com.magalu.application.use_cases.utils.output.StatusSuccess;
import com.magalu.domain.ValueObject.message.Message;
import com.magalu.domain.ValueObject.message.MessageGatewayInterface;
import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGatewayInterface;
import com.magalu.domain.entity.scheduled_message.status_scheduler.StatusSchedulerCancelled;
import com.magalu.domain.entity.scheduled_message.status_scheduler.StatusSchedulerCompleted;
import com.magalu.domain.entity.scheduled_message.status_scheduler.StatusSchedulerFailed;
import com.magalu.domain.validation.Notification;

import java.util.Objects;

/**
 * Responsável por
 * - validar dados da mensagem;
 * - criar registro na base de dados da mensagem agendada; e
 * - agendar o envio da mensagem.
 */
public class ScheduledMessageUseCase extends ScheduledMessageUseCaseAbstract {

    private final ScheduledMessageGatewayInterface scheduledMessageGateway;
    private final MessageGatewayInterface messageGateway;
    private Notification notification;

    public ScheduledMessageUseCase(
            final ScheduledMessageGatewayInterface scheduledMessageGateway,
            final MessageGatewayInterface messageGateway) {
        this.scheduledMessageGateway = Objects.requireNonNull(scheduledMessageGateway);
        this.messageGateway =  Objects.requireNonNull(messageGateway);
    }

    @Override
    public ScheduledMessageOutput execute(ScheduledMessageInput input) {
        this.notification = Notification.createNotification();

        final var scheduledMessage = ScheduledMessage.create(
                input.scheduledTime(),
                Message.create(input.message(), input.to(), notification),
                notification
        );
        hasError(() -> saveScheduledMessageInDatabase(scheduledMessage));
        hasError(() -> createScheduledMessage(scheduledMessage, () -> sendMessage(scheduledMessage)));

        return notification.hasError() ? createOutputFailed(scheduledMessage) : createOutputSuccess(scheduledMessage);
    }

    /**
     * Verifica se há algum erro em notification. Se houver, não executa a action.
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
     * Verifica se o status ainda é diferente de cancelado antes de executar o envio da mensagem.
     * Atualiza o status com "Completed" no banco de dados.
     */
    private void sendMessage(ScheduledMessage entity) {

        try{
            var currentStatus = scheduledMessageGateway.findById(entity.getUuid()).getStatusScheduler();
            if (currentStatus instanceof StatusSchedulerCancelled){
                return;
            }
            messageGateway.send(entity.getMessage());
            entity.changeStatus(StatusSchedulerCompleted.create());
            scheduledMessageGateway.save(entity);

        } catch (Exception e) {
            notification.append(
                    "Error when send message",
                    e.getMessage()
            );
        }
    }

    private ScheduledMessageOutput createOutputFailed(ScheduledMessage scheduledMessage){
        scheduledMessage.changeStatus(StatusSchedulerFailed.create());
        return ScheduledMessageOutput.create(
                scheduledMessage,
                StatusFailed.create(),
                notification
        );
    }

    private ScheduledMessageOutput createOutputSuccess(ScheduledMessage scheduledMessage) {
        return ScheduledMessageOutput.create(
                scheduledMessage,
                StatusSuccess.create(),
                notification
        );
    }
}
