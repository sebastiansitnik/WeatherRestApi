package java11.sda.WeatherRestApi.Location.external_api.weather_stack;

import java11.sda.WeatherRestApi.Location.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LocationJsonService {

    private final RestTemplateBuilder builder;

    @Autowired
    public LocationJsonService (RestTemplateBuilder builder){
        this.builder = builder;
    }

    private RestTemplate restTemplate(){
        return builder.build();
    }

    public Location getLocationFromExternalApi(String cityName){

        return restTemplate().getForObject(newURL(cityName), LocationResponseFromApi.class).getLocation();
    }

    private String newURL(String cityName){
        return url + changeCityNameForURL(cityName);
    }

    private String changeCityNameForURL(String cityName){
        return cityName.replace(" ","%20");
    }

    private final String url =URL + ACCESS_KEY + ACCESS_FOR_CITY;

    static final private String ACCESS_FOR_CITY = "&query=";

    static final private String ACCESS_KEY = "/current?access_key=d543410ffab3a814bf925aaca5f9e545";

    static final private String URL = "http://api.weatherstack.com";



}


