package com.alura.amqp.secondlistener.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class Payment {
    private UUID id = UUID.randomUUID();
    private String status;
    private Double price;
}
