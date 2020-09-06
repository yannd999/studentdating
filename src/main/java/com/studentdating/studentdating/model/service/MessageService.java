package com.studentdating.studentdating.model.service;

import com.studentdating.studentdating.model.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    List<MessageDTO> getMessages(String username);
    void setMessage(MessageDTO message);
}
