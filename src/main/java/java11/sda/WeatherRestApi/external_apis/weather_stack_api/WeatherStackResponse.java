package java11.sda.WeatherRestApi.external_apis.weather_stack_api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherStackResponse {
    @JsonProperty(value = "location")
    private WeatherStackLocation weatherStackLocation;
    @JsonProperty(value = "current")
    private WeatherStackWeather weatherStackWeather;

    public WeatherStackLocation getWeatherStackLocation() {
        return weatherStackLocation;
    }

    public void setWeatherStackLocation(WeatherStackLocation weatherStackLocation) {
        this.weatherStackLocation = weatherStackLocation;
    }

    public WeatherStackWeather getWeatherStackWeather() {
        return weatherStackWeather;
    }

    public void setWeatherStackWeather(WeatherStackWeather weatherStackWeather) {
        this.weatherStackWeather = weatherStackWeather;
    }

}
