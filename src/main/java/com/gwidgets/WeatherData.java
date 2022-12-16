package com.gwidgets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {
    @JsonProperty("current_weather")
    CurrentWeahter currentWeahter;

    public CurrentWeahter getCurrentWeahter() {
        return currentWeahter;
    }

    public void setCurrentWeahter(CurrentWeahter currentWeahter) {
        this.currentWeahter = currentWeahter;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrentWeahter {
        private float temperature;

        public float getTemperature() {
            return temperature;
        }

        public void setTemperature(float temperature) {
            this.temperature = temperature;
        }
    }
}
