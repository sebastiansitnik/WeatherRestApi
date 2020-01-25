package java11.sda.WeatherRestApi.Weather.external_api.weather_stack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java11.sda.WeatherRestApi.Weather.Weather;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponseFromApi {

    @JsonProperty(value = "current")
    private Weather weather;
    @JsonProperty(value = "location")
    private WeatherDate date;

    public WeatherResponseFromApi() {
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public WeatherDate getDate() {
        return date;
    }

    public void setDate(WeatherDate date) {
        this.date = date;
    }
}
