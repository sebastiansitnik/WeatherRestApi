package java11.sda.WeatherRestApi.Weather;

import java11.sda.WeatherRestApi.Location.Location;
import org.springframework.stereotype.Component;

@Component
public class WeatherDTOTransformer {

    public Weather toEntity(WeatherDTO weatherDTO){
        Weather weather = new Weather();

        weather.setTemperature(weatherDTO.getPressure());
        weather.setPressure(weatherDTO.getPressure());
        weather.setHumidity(weatherDTO.getHumidity());
        weather.setWind_direction(weatherDTO.getWind_direction());
        weather.setWind_speed(weatherDTO.getWind_speed());
        weather.setDate(weatherDTO.getDate());

        Location location = new Location();
        location.setId(weatherDTO.getLocationId());
        weather.setLocation(location);
        return weather;
    }




    public WeatherDTO toDto(Weather weather){
        WeatherDTO weatherDTO = new WeatherDTO();
        weatherDTO.setId(weather.getId());
        weatherDTO.setTemperature(weather.getTemperature());
        weatherDTO.setPressure(weather.getPressure());
        weatherDTO.setHumidity(weather.getHumidity());
        weatherDTO.setWind_direction(weather.getWind_direction());
        weatherDTO.setWind_speed(weather.getWind_speed());
        weatherDTO.setDate(weather.getDate());
        weatherDTO.setLocationName(weather.getLocation().getCityName());
        weatherDTO.setLocationId(weather.getLocation().getId());

        return weatherDTO;
    }
}
