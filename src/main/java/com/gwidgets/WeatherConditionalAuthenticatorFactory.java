package com.gwidgets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.Config;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticator;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.List;

public class WeatherConditionalAuthenticatorFactory implements ConditionalAuthenticatorFactory {

    public static final String CONF_MIN_TEMPERATURE = "min_temperature";

    private final static HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1).build();

    private final static ObjectMapper mapper = new ObjectMapper();

    private static final WeatherConditionalAuthenticator INSTANCE = new WeatherConditionalAuthenticator(mapper, httpClient);

    @Override
    public ConditionalAuthenticator getSingleton() {
        return INSTANCE;
    }

    @Override
    public String getDisplayType() {
        return "Condition - weather";
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return new AuthenticationExecutionModel.Requirement[]{AuthenticationExecutionModel.Requirement.REQUIRED,
                AuthenticationExecutionModel.Requirement.DISABLED};
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public String getHelpText() {
        return "Checks the weather for the configured location, and compares it with the configured value";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        var minTemperature = new ProviderConfigProperty();
        minTemperature.setName(CONF_MIN_TEMPERATURE);
        minTemperature.setLabel("Minimum Temperature (in °C)");
        minTemperature.setType(ProviderConfigProperty.STRING_TYPE);
        minTemperature.setHelpText("The minimum temperature that should trigger the condition to be true");
        return Arrays.asList(
                minTemperature
        );
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
        return "good-weather";
    }
}
