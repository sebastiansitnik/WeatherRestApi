package java11.sda.WeatherRestApi.Weather.external_api.weather_stack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDate {

    @JsonProperty (value = "localtime")
    private String localTime;

    public WeatherDate() {
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }
}
