package com.studentdating.studentdating.model.db;

import com.studentdating.studentdating.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}

