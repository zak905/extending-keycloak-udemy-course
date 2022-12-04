package com.gwidgets;

import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.policy.PasswordPolicyProvider;
import org.keycloak.policy.PolicyError;

public class DollarPasswordPolicyProvider implements PasswordPolicyProvider  {
    @Override
    public PolicyError validate(RealmModel realm, UserModel user, String password) {
        return validate(null, password);
    }

    @Override
    public PolicyError validate(String user, String password) {
        return password.startsWith("$") && password.endsWith("$") ? null : new PolicyError("password should start and end with $ character");
    }

    @Override
    public Object parseConfig(String value) {
        return null;
    }

    @Override
    public void close() {

    }
}
