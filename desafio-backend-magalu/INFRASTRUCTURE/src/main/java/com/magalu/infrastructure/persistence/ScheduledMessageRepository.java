package com.magalu.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledMessageRepository extends JpaRepository<ScheduledMessageJpaEntity, String> {

}
