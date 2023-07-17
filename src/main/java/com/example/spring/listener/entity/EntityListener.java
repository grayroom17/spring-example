package com.example.spring.listener.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EntityListener {

    @EventListener(condition = "#root.args[0].accessType.name() == 'READ'")
    public void acceptEntity(EntityEvent entityEvent) {
        log.info("Entity: " + entityEvent);
    }

}
