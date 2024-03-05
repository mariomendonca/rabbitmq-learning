package com.alura.amqp.publisher.controller;

import com.alura.amqp.publisher.domain.Payment;
import com.alura.amqp.publisher.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping
    public Payment doPayment(@RequestBody Payment payment) {
        return paymentService.doPayment(payment);
    }
}
