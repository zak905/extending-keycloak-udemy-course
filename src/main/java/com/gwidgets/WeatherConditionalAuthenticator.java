package com.gwidgets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.utils.StringUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherConditionalAuthenticator implements ConditionalAuthenticator {

    private static Logger logger = Logger.getLogger("com.gwidgets.WeatherConditionalAuthentication");

    private final ObjectMapper objectMapper;

    private final HttpClient client;

    public WeatherConditionalAuthenticator(ObjectMapper objectMapper, HttpClient client) {
        this.objectMapper = objectMapper;
        this.client = client;
    }

    @Override
    public boolean matchCondition(AuthenticationFlowContext context) {
        var authenticatorConfig = context.getAuthenticatorConfig();
        if (authenticatorConfig == null) {
            return false;
        }
        var config = authenticatorConfig.getConfig();
        var currentUser = context.getUser();
        var latitude = currentUser.getFirstAttribute("latitude");
        var longitude = currentUser.getFirstAttribute("longitude");
        if (StringUtil.isBlank(latitude) || StringUtil.isBlank(longitude)) {
            return false;
        }
        float temperature;
        try {
            temperature = getWeatherTemperature(latitude, longitude);
        } catch (Exception e) {
            logger.log(Level.WARNING, "error getting weather temperature", e);
            return false;
        }
        var minTemperature = Float.parseFloat(config.get(WeatherConditionalAuthenticatorFactory.CONF_MIN_TEMPERATURE));
        //I am using the !negate operator just to show that we need to return true if the condition does not match
        //otherwise we can just flip the comparison operator to >
        return !(minTemperature <= temperature);
    }

    @Override
    public void action(AuthenticationFlowContext context) {

    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {}

    @Override
    public void close() {

    }

    private float getWeatherTemperature(String latitude, String longitude) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.open-meteo.com/v1/forecast?latitude="+latitude+"&longitude="+longitude+"&current_weather=true"))
                .GET()
                .build();
        logger.log(Level.INFO, "retrieving weather forecast for latitude {0}, longitude {1}", new String[]{latitude, longitude});

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        var temperature = objectMapper.readValue(response.body(), WeatherData.class).getCurrentWeahter().getTemperature();

        logger.log(Level.INFO, "got status {0}, temperature {1}", new Object[]{response.statusCode(), temperature});

        return temperature;
    }
}
