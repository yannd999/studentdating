package com.studentdating.studentdating.model.service;

import com.studentdating.studentdating.model.db.MessageRepository;
import com.studentdating.studentdating.model.dto.MessageDTO;
import com.studentdating.studentdating.model.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private final MessageRepository repository;
    
    public MessageServiceImpl(MessageRepository repository) {
        this.repository = repository;
    }
    
    public List<MessageDTO> getMessages(String username) {
        List<MessageDTO> messages = new ArrayList<>();
        for (Message m: repository.findAll()) {
            System.out.println("Message: " + m.getOntvanger()); //todo
            if (m.getOntvanger().equals(username) || m.getZender().equals(username)) messages.add(convert(m));
        }
        return messages;
    }

    public void setMessage(MessageDTO message) {
        Message m = new Message();
        m.setZender(message.getZender());
        m.setOntvanger(message.getOntvanger());
        m.setBericht(message.getBericht());
        repository.save(m);
    }

    private MessageDTO convert(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setBericht(message.getBericht());
        dto.setZender(message.getZender());
        dto.setOntvanger(message.getOntvanger());
        return dto;
    }

    private Message convertBack(MessageDTO message) {
        Message dto = new Message();
        dto.setId(message.getId());
        dto.setBericht(message.getBericht());
        dto.setZender(message.getZender());
        dto.setOntvanger(message.getOntvanger());
        return dto;
    }
}
