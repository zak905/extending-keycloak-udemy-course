package com.gwidgets;

import org.keycloak.Config;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

public class LegacyApiUserStorageProviderFactory implements UserStorageProviderFactory<LegacyApiUserStorageProvider> {

    private String apiBaseUrl;

    @Override
    public LegacyApiUserStorageProvider create(KeycloakSession keycloakSession, ComponentModel componentModel) {
        return new LegacyApiUserStorageProvider(keycloakSession, apiBaseUrl, componentModel.getId());
    }

    @Override
    public void init(Config.Scope config) {
        apiBaseUrl = config.get("url");
    }

    @Override
    public String getId() {
        return "legacy-api";
    }
}
