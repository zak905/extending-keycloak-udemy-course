package com.gwidgets;

import com.gwidgets.domain.Subscription;
import com.gwidgets.spi.GreetingProvider;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.jpa.entities.UserEntity;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.UUID;

public class GreetingEventListenerProvider implements EventListenerProvider {


    private final GreetingProvider greetingProvider;

    private final EntityManager entityManager;

    public GreetingEventListenerProvider(GreetingProvider greetingProvider, EntityManager entityManager) {
        this.greetingProvider = greetingProvider;
        this.entityManager = entityManager;
    }

    @Override
    public void onEvent(Event event) {
        if (event.getType() == EventType.LOGIN) {
         greetingProvider.sayHi();
        } else if (event.getType() == EventType.REGISTER) {
            Subscription subscription = new Subscription();
            subscription.setId(UUID.randomUUID());
            UserEntity userEntity = new UserEntity();
            userEntity.setId(event.getUserId());
            subscription.setUser(userEntity);
            subscription.setCreated(LocalDateTime.now());
            entityManager.persist(subscription);
        }
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {

    }

    @Override
    public void close() {

    }
}
