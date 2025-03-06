package com.magalu.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledMessageRepository extends JpaRepository<ScheduledMessageJpaEntity, String> {

}
