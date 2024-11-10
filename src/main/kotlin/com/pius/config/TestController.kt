package com.pius.config

import com.pius.Greeting
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.support.GenericMessage
import org.springframework.stereotype.Controller

@Controller
class TestController(
    private val messageTemplate: SimpMessagingTemplate
) {

    private val logger = LoggerFactory.getLogger(javaClass)
    @MessageMapping("/hello")
    fun test(message: String, attributes: MutableMap<String, Any>): Greeting {
        logger.info("Received message: $message")
        messageTemplate.convertAndSend("/topic/greetings", Greeting(message))
        messageTemplate.convertAndSendToUser(attributes.getOrDefault("name", ""), "/queue/greetings", Greeting(message))
        return Greeting(message)
    }
}