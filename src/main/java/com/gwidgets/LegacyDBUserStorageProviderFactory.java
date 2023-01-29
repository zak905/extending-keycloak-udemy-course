package com.gwidgets;

import org.keycloak.Config;
import org.keycloak.component.ComponentModel;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

public class LegacyDBUserStorageProviderFactory implements UserStorageProviderFactory<LegacyDBUserStorageProvider>  {

    @Override
    public LegacyDBUserStorageProvider create(KeycloakSession session, ComponentModel componentModel) {
        return new LegacyDBUserStorageProvider(session.getProvider(JpaConnectionProvider.class, "legacy-db").getEntityManager(), componentModel.getId());
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void close() {}

    @Override
    public String getId() {
        return "legacy-db-uf";
    }
}
