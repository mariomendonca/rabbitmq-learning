package com.alura.amqp.publisher.service;

import com.alura.amqp.publisher.domain.Payment;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {
    private final RabbitTemplate rabbitTemplate;

    public Payment doPayment(Payment payment) {
//        Message message = new Message(("payment done - id: " + payment.getId()).getBytes());
        rabbitTemplate.convertAndSend("payments.exchange", "", payment);
        return payment;
    }
}
