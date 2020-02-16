package java11.sda.WeatherRestApi.external_apis;

import java11.sda.WeatherRestApi.location.Location;
import java11.sda.WeatherRestApi.weather.Weather;
import java11.sda.WeatherRestApi.external_apis.weather_stack_api.WeatherStackApi;
import java11.sda.WeatherRestApi.external_apis.weather_stack_api.WeatherStackLocation;
import java11.sda.WeatherRestApi.external_apis.weather_stack_api.WeatherStackResponse;
import java11.sda.WeatherRestApi.external_apis.weather_stack_api.WeatherStackWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExternalApisService {

    private final RestTemplateBuilder builder;


    @Autowired
    public ExternalApisService(RestTemplateBuilder builder) {
        this.builder = builder;
    }

    @Bean
    private RestTemplate restTemplate() {
        return builder.build();
    }

    private WeatherStackResponse getResponseFromWeatherStack(String cityName) {
        String url = WeatherStackApi.URL + WeatherStackApi.ACCESS_KEY + WeatherStackApi.ACCESS_FOR_CITY + changeCityNameForURL(cityName);

        return restTemplate().getForObject(url, WeatherStackResponse.class);

    }

    public Location getLocationFromExternalApi(String cityName) {
        WeatherStackResponse wsr = getResponseFromWeatherStack(cityName);
        WeatherStackLocation wsrLocation = wsr.getWeatherStackLocation();

        Location location = new Location();
        location.setId("1");
        location.setLongitude(Float.parseFloat(wsrLocation.getLon()));
        location.setLatitude(Float.parseFloat(wsrLocation.getLat()));
        location.setCityName(wsrLocation.getName());
        location.setRegion(wsrLocation.getRegion());
        location.setCountry(wsrLocation.getCountry());

        return location;
    }

    public Weather getWeatherFromExternalApis(String cityName) {
        WeatherStackResponse wsr = getResponseFromWeatherStack(cityName);
        WeatherStackWeather wsrWeather = wsr.getWeatherStackWeather();

        Weather weather = new Weather();
        weather.setTemperature(Float.parseFloat(wsrWeather.getTemperature()));
        weather.setPressure(Float.parseFloat(wsrWeather.getPressure()));
        weather.setHumidity(Float.parseFloat(wsrWeather.getHumidity()));
        weather.setWindDirection(wsrWeather.getWindDir());
        weather.setWindSpeed(Float.parseFloat(wsrWeather.getWindSpeed()));
        weather.setDate(wsr.getWeatherStackLocation().getLocaltime());

        return weather;
    }


    private String changeCityNameForURL(String cityName) {
        return cityName.replace(targetCharForReplacement, replacement);
    }

    private final static String targetCharForReplacement = " ";
    private final static String replacement = "%20";


}


