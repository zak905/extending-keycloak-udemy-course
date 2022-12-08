package com.gwidgets;

import org.keycloak.Config;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class FeelingSurveyRequiredActionProviderFactory implements RequiredActionFactory {
    @Override
    public String getDisplayText() {
        return "How do you feel ? ";
    }

    @Override
    public RequiredActionProvider create(KeycloakSession session) {
        return new FeelingSurveyRequiredActionProvider();
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
        return "feeling-survey";
    }
}
