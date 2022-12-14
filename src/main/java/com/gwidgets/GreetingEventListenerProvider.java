package com.gwidgets;

import com.gwidgets.spi.GreetingProvider;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;

public class GreetingEventListenerProvider implements EventListenerProvider {


    private final GreetingProvider greetingProvider;

    public GreetingEventListenerProvider(GreetingProvider greetingProvider) {
        this.greetingProvider = greetingProvider;
    }

    @Override
    public void onEvent(Event event) {
        if (event.getType() == EventType.LOGIN) {
         greetingProvider.sayHi();
        }
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {

    }

    @Override
    public void close() {

    }
}
