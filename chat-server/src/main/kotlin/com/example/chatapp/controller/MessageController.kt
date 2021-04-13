package com.example.chatapp.controller

import com.example.chatapp.domain.Message
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class MessageController {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MessageController::javaClass.name)
    }

    @MessageMapping("/send")
    @SendTo("/topic/public")
    fun pushMessage(message: Message): Message {
        logger.info("New message date={} name={} message={}", message.date, message.name, message.message)
        return message
    }

}
