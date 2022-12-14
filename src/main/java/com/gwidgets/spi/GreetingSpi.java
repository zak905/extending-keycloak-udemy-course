package com.gwidgets.spi;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

public class GreetingSpi implements Spi {
    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {
        return "greeting";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return GreetingProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return GreetingProviderFactory.class;
    }
}
