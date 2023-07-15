package com.example.spring.listener.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EntityEvent extends ApplicationEvent {

    @Getter
    AccessType accessType;

    public EntityEvent(Object entity, AccessType accessType) {
        super(entity);
        this.accessType = accessType;
    }

}
