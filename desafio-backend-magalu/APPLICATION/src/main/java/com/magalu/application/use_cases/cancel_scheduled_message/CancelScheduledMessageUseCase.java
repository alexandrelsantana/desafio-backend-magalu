package com.magalu.application.use_cases.cancel_scheduled_message;

import com.magalu.application.use_cases.utils.output.StatusFailed;
import com.magalu.application.use_cases.utils.output.StatusSuccess;
import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGatewayInterface;
import com.magalu.domain.entity.scheduled_message.status_scheduler.StatusSchedulerCancelled;
import com.magalu.domain.entity.scheduled_message.status_scheduler.StatusSchedulerScheduled;
import com.magalu.domain.validation.Notification;

public class CancelScheduledMessageUseCase extends CancelScheduledMessageUseCaseAbstract {

    final ScheduledMessageGatewayInterface scheduledMessageGateway;
    private Notification notification;

    public CancelScheduledMessageUseCase(ScheduledMessageGatewayInterface scheduledMessageGateway) {
        this.scheduledMessageGateway =  scheduledMessageGateway;
    }

    /**
     * Responsável por cancelar uma mensagem agendada.
     * Como toda a mensagem agendada em mémoria valida o status antes de enviar, somente encaminhando mensagens com
     * status diferente de cancelado, o cancelamento apenas insere o status cancelado no banco de dados.
     */
    @Override
    public CancelScheduledMessageOutput execute(final CancelScheduledMessageInput input) {
        this.notification = Notification.createNotification();

        ScheduledMessage scheduledMessage = getScheduledMessage(input);
        hasError(() -> checkStatus(scheduledMessage));
        hasError(() -> cancelScheduled(scheduledMessage));

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

    private void checkStatus(final ScheduledMessage scheduledMessage){

        if (scheduledMessage.getStatusScheduler() instanceof StatusSchedulerCancelled){
            notification.append(
                    "Error when cancel scheduled",
                    "Scheduled message is already cancelled"
            );
            return;
        }

        if (!(scheduledMessage.getStatusScheduler() instanceof StatusSchedulerScheduled)){
            notification.append(
                    "Error when cancel scheduled",
                    "The scheduled message cannot be canceled as it is not scheduled"
            );
        }

    }

    private void cancelScheduled(final ScheduledMessage scheduledMessage){
        try{
            scheduledMessage.changeStatus(StatusSchedulerCancelled.create());
            scheduledMessageGateway.save(scheduledMessage);
        }catch (Exception e){
            notification.append(
                    "Error when cancel scheduled in database",
                    e.getMessage()
            );
        }
    }

    private ScheduledMessage getScheduledMessage(CancelScheduledMessageInput input) {
        ScheduledMessage scheduledMessage = null;
        try {
            scheduledMessage = scheduledMessageGateway.findById(input.id());

        } catch (Exception e) {
            notification.append(
                    "Error when retrieve scheduled message in database",
                    e.getMessage()
            );
        }

        if (scheduledMessage == null){
            notification.append(
                    "Error when retrieve scheduled message in database",
                    String.format("No data in database. uuid: %s", input.id())
            );
        }
        return scheduledMessage;
    }

    private CancelScheduledMessageOutput createOutputFailed(ScheduledMessage scheduledMessage){
        return CancelScheduledMessageOutput.create(
                scheduledMessage,
                StatusFailed.create(),
                notification
        );
    }

    private CancelScheduledMessageOutput createOutputSuccess(ScheduledMessage scheduledMessage) {
        return CancelScheduledMessageOutput.create(
                scheduledMessage,
                StatusSuccess.create(),
                notification
        );
    }
}
