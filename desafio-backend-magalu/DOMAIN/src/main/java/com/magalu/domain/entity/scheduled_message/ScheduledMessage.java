package com.magalu.domain.entity.scheduled_message;

import com.magalu.domain.ValueObject.message.Message;
import com.magalu.domain.entity.Entity;
import com.magalu.domain.entity.scheduled_message.status_scheduler.StatusScheduler;
import com.magalu.domain.entity.scheduled_message.status_scheduler.StatusSchedulerCreated;
import com.magalu.domain.validation.Notification;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduledMessage extends Entity {

    private final LocalDateTime scheduledTime;
    private final Message message;
    public StatusScheduler statusScheduler;

    private ScheduledMessage(
            final String uuid,
            final LocalDateTime scheduledTime,
            final Message message,
            final StatusScheduler statusScheduler) {
        super(uuid);
        this.scheduledTime = scheduledTime;
        this.message = message;
        this.statusScheduler = statusScheduler;
    }

    public static ScheduledMessage create(
            final LocalDateTime scheduledTime,
            final Message message,
            final Notification notification){

        ScheduledMessage scheduledMessage = new ScheduledMessage(
                uuid(),
                scheduledTime,
                message,
                StatusSchedulerCreated.create()
        );

        scheduledMessage.validate(notification);
        if (notification.hasError()){
            scheduledMessage.cleanUuid();
        }

        return scheduledMessage;
    }

    public static ScheduledMessage create(
            final String uuid,
            final LocalDateTime scheduledTime,
            final String message,
            final String to,
            final Notification notification){

        ScheduledMessage scheduledMessage = new ScheduledMessage(
                uuid,
                scheduledTime,
                Message.create(message, to, notification),
                StatusSchedulerCreated.create());

        scheduledMessage.validate(notification);
        return scheduledMessage;
    }

    public static ScheduledMessage create(
            final String uuid,
            final LocalDateTime scheduledTime,
            final Message message,
            final StatusScheduler statusScheduler){

        return  new ScheduledMessage(
                uuid,
                scheduledTime,
                message,
                statusScheduler);
    }

    public void validate(Notification notification){
        new SchedulerMessageValidator(notification).validate(this);
    }

    public void changeStatus(StatusScheduler statusScheduler){
        this.statusScheduler = statusScheduler;
    }

    private void cleanUuid(){
        this.uuid = null;
    }

    public String getMessageTo(){
        return this.message.getText();
    }

    public String getMessageText(){
        return this.message.getTo();
    }
}
