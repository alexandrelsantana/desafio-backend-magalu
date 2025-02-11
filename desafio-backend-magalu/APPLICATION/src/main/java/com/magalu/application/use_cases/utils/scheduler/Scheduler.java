package com.magalu.application.use_cases.utils.scheduler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class Scheduler {

    public ScheduledFuture<?>  createTask(
            final LocalDateTime scheduledTime,
            final Runnable task){

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        ZonedDateTime targetDateTime = scheduledTime.atZone(ZoneId.systemDefault());
        long delay = targetDateTime.toInstant().toEpochMilli() - now.toInstant().toEpochMilli();

        return scheduler.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

}
