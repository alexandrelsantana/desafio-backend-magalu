package com.magalu.infrastructure.persistence;

import com.magalu.domain.entity.scheduled_message.StatusScheduler;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="scheduled_message")
public class ScheduledMessageJpaEntity {

    @Id
    @Column(name = "uuid")
    String UUID;

    @Column(name = "scheduled_time")
    private LocalDateTime scheduledTime;

    @Column(name="message")
    private String message;

    @Column(name="destinatary")
    private String to;

    @Column(name="status_scheduler")
    @Enumerated(EnumType.STRING)
    public StatusScheduler statusScheduler;

    public ScheduledMessageJpaEntity() {
    }

    public ScheduledMessageJpaEntity(
            String UUID,
            LocalDateTime scheduledTime,
            StatusScheduler statusScheduler,
            String message,
            String to) {
        this.UUID = UUID;
        this.scheduledTime = scheduledTime;
        this.message = message;
        this.statusScheduler = statusScheduler;
        this.to = to;
    }

}
