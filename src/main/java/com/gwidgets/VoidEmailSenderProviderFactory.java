package com.gwidgets;

import org.keycloak.Config;
import org.keycloak.email.EmailSenderProvider;
import org.keycloak.email.EmailSenderProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class VoidEmailSenderProviderFactory implements EmailSenderProviderFactory {

    private static Logger logger = Logger.getLogger("com.gwidgets.EmailSenderProvider");

    @Override
    public EmailSenderProvider create(KeycloakSession session) {
        return new VoidEmailSenderProvider();
    }

    @Override
    public void init(Config.Scope config) {
        logger.log(Level.INFO, "Email sender initialized");
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
