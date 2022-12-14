package com.gwidgets;

import com.gwidgets.spi.GreetingProvider;
import com.gwidgets.spi.GreetingProviderFactory;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class DefaultGreetingProviderFactory implements GreetingProviderFactory {
    @Override
    public GreetingProvider create(KeycloakSession session) {
        return new DefaultGreetingProvider(session.getContext());
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
        return "default";
    }
}
