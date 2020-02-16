package java11.sda.WeatherRestApi.weather;

import java11.sda.WeatherRestApi.location.Location;
import org.springframework.stereotype.Component;

@Component
public class WeatherDTOTransformer {

    public Weather toEntity(WeatherDTO weatherDTO) {
        Location location = new Location();
        location.setId(weatherDTO.getLocationId());

        Weather weather = new Weather();
        weather.setTemperature(weatherDTO.getTemperature());
        weather.setPressure(weatherDTO.getPressure());
        weather.setHumidity(weatherDTO.getHumidity());
        weather.setWindDirection(weatherDTO.getWindDirection());
        weather.setWindSpeed(weatherDTO.getWindSpeed());
        weather.setDate(weatherDTO.getDate());
        weather.setLocation(location);
        weather.setId(weatherDTO.getId());

        return weather;
    }

    public WeatherDTO toDto(Weather weather) {

        return new WeatherDTO(weather.getId(),
                weather.getTemperature(),
                weather.getPressure(),
                weather.getHumidity(),
                weather.getWindDirection(),
                weather.getWindSpeed(),
                weather.getDate(),
                weather.getLocation().getCityName(),
                weather.getLocation().getId());
    }
}
