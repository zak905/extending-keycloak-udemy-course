package com.gwidgets;

import com.gwidgets.spi.GreetingProvider;
import org.keycloak.models.KeycloakContext;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultGreetingProvider implements GreetingProvider {

    private static Logger logger = Logger.getLogger("com.gwidgets.DefaultGreetingProvider");

    private final KeycloakContext context;

    public DefaultGreetingProvider(KeycloakContext context) {
        this.context = context;
    }

    @Override
    public void sayHi() {
        if (context.getAuthenticationSession() != null) {
            logger.log(Level.INFO, "Hi there {0}!", context.getAuthenticationSession().getAuthenticatedUser().getFirstName());
        } else {
            logger.log(Level.INFO, "Hi there stranger!");
        }
    }

    @Override
    public void close() {

    }
}
