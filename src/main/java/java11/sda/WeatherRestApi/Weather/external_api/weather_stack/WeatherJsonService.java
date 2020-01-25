package java11.sda.WeatherRestApi.Weather.external_api.weather_stack;

import java11.sda.WeatherRestApi.Weather.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherJsonService {

    private final RestTemplateBuilder builder;

    @Autowired
    public WeatherJsonService (RestTemplateBuilder builder){
        this.builder = builder;
    }

    private RestTemplate restTemplate(){
        return builder.build();
    }

    public Weather getWeatherFromApi(String cityName){
        cityName = changeCityNameForURL(cityName);
        String url = finalURL(cityName);

        WeatherResponseFromApi weatherResponseFromApi = getResponse(url);
        Weather weather = weatherResponseFromApi.getWeather();
        String date = changeDate(weatherResponseFromApi.getDate().getLocalTime());
        weather.setDate(date);

        return weather;
    }

    private String changeDate(String date){
        return date.substring(0,10);
    }

    private WeatherResponseFromApi getResponse(String url){
        return restTemplate().getForObject(url,WeatherResponseFromApi.class);
    }

    private String finalURL(String cityName){
        String url = URL + ACCESS_KEY + ACCESS_FOR_CITY;
        return url + cityName;
    }

    private String changeCityNameForURL(String cityName){
        return cityName.replace(" ","%20");
    }

    static final private String ACCESS_FOR_CITY = "&query=";

    static final private String ACCESS_KEY = "/current?access_key=d543410ffab3a814bf925aaca5f9e545";

    static final private String URL = "http://api.weatherstack.com";
}
