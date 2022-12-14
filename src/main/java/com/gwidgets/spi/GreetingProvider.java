package com.gwidgets.spi;

import org.keycloak.provider.Provider;

public interface GreetingProvider extends Provider {
    void sayHi();
}
