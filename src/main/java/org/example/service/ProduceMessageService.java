package org.example.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.userimp.UserRabbitMQ;
import org.example.model.UserImp;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.Queue;
@Service
public class ProduceMessageService {

    private final RabbitTemplate rabbitTemplate;

    public ProduceMessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendUserRegistered(UserRabbitMQ user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        rabbitTemplate.convertAndSend("myTopicExchange","myRoutingKey.user", objectMapper.writeValueAsBytes(user));
    }
}