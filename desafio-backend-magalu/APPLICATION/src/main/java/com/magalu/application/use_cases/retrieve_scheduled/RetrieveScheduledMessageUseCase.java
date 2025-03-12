package com.magalu.application.use_cases.retrieve_scheduled;

import com.magalu.application.use_cases.utils.output.StatusFailed;
import com.magalu.application.use_cases.utils.output.StatusSuccess;
import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGatewayInterface;
import com.magalu.domain.validation.Notification;

public class RetrieveScheduledMessageUseCase extends RetrieveScheduledMessageUseCaseAbstract {

    final ScheduledMessageGatewayInterface scheduledMessageGateway;
    private Notification notification;
    private ScheduledMessage scheduledMessage;


    public RetrieveScheduledMessageUseCase(ScheduledMessageGatewayInterface scheduledMessageGateway) {
        this.scheduledMessageGateway =  scheduledMessageGateway;
    }

    /**
     * Responsável por retornar uma mensagem agendada.
     */
    @Override
    public RetrieveScheduledMessageOutput execute(final RetrieveScheduledMessageInput input) {
        this.notification = Notification.createNotification();

        validateInput(input, notification);
        hasError(() -> getScheduledMessage(input));

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

    private void validateInput(RetrieveScheduledMessageInput input, Notification notification) {
        new RetrieveScheduledMessageInputValidator(notification).validate(input);
    }

    private void getScheduledMessage(RetrieveScheduledMessageInput input) {
        try {
            scheduledMessage = scheduledMessageGateway.findById(input.id());

        } catch (Exception e) {
            notification.append(
                    "Error when retrieve scheduled message in database",
                    e.getMessage()
            );
            return;
        }

        if (scheduledMessage == null){
            notification.append(
                    "Error when retrieve scheduled message in database",
                    String.format("No data in database. uuid: %s", input.id())
            );
        }
    }

    private RetrieveScheduledMessageOutput createOutputFailed(ScheduledMessage scheduledMessage){
        return RetrieveScheduledMessageOutput.create(
                scheduledMessage,
                StatusFailed.create(),
                notification
        );
    }

    private RetrieveScheduledMessageOutput createOutputSuccess(ScheduledMessage scheduledMessage) {
        return RetrieveScheduledMessageOutput.create(
                scheduledMessage,
                StatusSuccess.create(),
                notification
        );
    }
}
