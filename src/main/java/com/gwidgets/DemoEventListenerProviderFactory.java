package com.gwidgets;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class DemoEventListenerProviderFactory implements EventListenerProviderFactory{
    @Override
    public EventListenerProvider create(KeycloakSession session) {
        return new DemoEventListenerProvider();
    }
    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "demo-listener";
    }
}
