package com.alura.amqp.secondlistener.listener;

import com.alura.amqp.secondlistener.domain.Payment;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {
    @RabbitListener(queues = "payments.order-rating")
    public void messageListener(Payment payment) {
        System.out.println("status = " + payment.getStatus());
        if ("done".equals(payment.getStatus())) {
            throw new RuntimeException("error");
        }
        String message = "Id: %s | Price: %s | Status: %s".formatted(payment.getId(), payment.getPrice(), payment.getStatus());
        System.out.println("message = " + message);
    }
}

