package com.magalu.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name="scheduled_message")
public class ScheduledMessageJpaEntity {

    @Id
    @Column(name = "uuid")
    String UUID;

    @Column(name = "scheduled_time")
    private LocalDateTime scheduledTime;

    @Column(name="message")
    private String message;

    @Column(name="status_scheduler")
    public String statusScheduler;

}
