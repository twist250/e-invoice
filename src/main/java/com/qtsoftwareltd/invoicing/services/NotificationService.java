package com.qtsoftwareltd.invoicing.services;

import com.qtsoftwareltd.invoicing.entities.Invoice;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private static final Logger logger = LogManager.getLogger(NotificationService.class);
    private final RabbitTemplate rabbitTemplate;

    public void send(Invoice in) {
        logger.info("Sending invoice: {}", in);
        rabbitTemplate.convertAndSend("invoices", in);
    }

    @RabbitListener(queues = "invoices")
    public void listen(Invoice in) {
        logger.info("Recorded invoice: {}", in);
    }
}
