package com.alura.amqp.secondlistener.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Queue orderRatingQueue() {
        return QueueBuilder
                .nonDurable("payments.order-rating")
                .deadLetterExchange("payments.dlx")
                .build();
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange("payments.exchange").build();
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return ExchangeBuilder.fanoutExchange("payments.dlx").build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder
                .nonDurable("payments.order-rating-dlq")
                .build();
    }

    @Bean
    public Binding bindingPaymentOrder(FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(orderRatingQueue()).to(fanoutExchange);
    }

    @Bean
    public Binding bindingDlqPaymentOrder(FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
    }

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> adminInitialize(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }
}
