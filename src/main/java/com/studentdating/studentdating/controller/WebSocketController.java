package com.studentdating.studentdating.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @MessageMapping("/hello")
    @SendTo("/blogtopic")
    public String commenter(String commenter) {
        return commenter;
    }
}
