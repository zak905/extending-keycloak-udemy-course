package com.gwidgets;

import com.gwidgets.domain.Subscription;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;

import java.util.Arrays;
import java.util.List;

public class SubscriptionJpaEntityProvider implements JpaEntityProvider {
    @Override
    public List<Class<?>> getEntities() {
        return Arrays.asList(Subscription.class);
    }

    @Override
    public String getChangelogLocation() {
        return "META-INF/changelog.xml";
    }

    @Override
    public String getFactoryId() {
        return "subscription";
    }

    @Override
    public void close() {

    }
}
