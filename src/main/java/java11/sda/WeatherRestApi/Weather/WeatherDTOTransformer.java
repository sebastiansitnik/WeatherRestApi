package java11.sda.WeatherRestApi.Weather;

import java11.sda.WeatherRestApi.Location.Location;
import org.springframework.stereotype.Component;

@Component
public class WeatherDTOTransformer {

    public Weather toEntity(WeatherDTO weatherDTO){
        Location location = new Location();
        location.setId(weatherDTO.getLocationId());


        return new Weather(weatherDTO.getTemperature(),
                weatherDTO.getPressure(),weatherDTO.getHumidity(),
                weatherDTO.getWindDirection(),
                weatherDTO.getWindSpeed(),
                weatherDTO.getDate(),location);
    }




    public WeatherDTO toDto(Weather weather){

        return new WeatherDTO(weather.getTemperature(),
                weather.getPressure(),
                weather.getHumidity(),
                weather.getWindDirection(),
                weather.getWindSpeed(),
                weather.getDate(),
                weather.getLocation().getCityName(),
                weather.getLocation().getId());
    }
}
