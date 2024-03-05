package com.alura.amqp.subscriber.listener;

import com.alura.amqp.subscriber.domain.Payment;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {
    @RabbitListener(queues = "payments.order-details")
    public void messageListener(Payment payment) {
        String message = "Id: %s | Price: %s | Status: %s".formatted(payment.getId(), payment.getPrice(), payment.getStatus());
        System.out.println("message = " + message);
    }
}
