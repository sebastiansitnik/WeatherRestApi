package java11.sda.WeatherRestApi.weather;

import java11.sda.WeatherRestApi.location.Location;
import java11.sda.WeatherRestApi.location.LocationRepository;
import java11.sda.WeatherRestApi.location.LocationService;
import java11.sda.WeatherRestApi.location.NoLocationInDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeatherDTOTransformer {

    private final LocationRepository locationRepository;

    @Autowired
    public WeatherDTOTransformer(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Weather toEntity(WeatherDTO weatherDTO) {
        Location location = locationRepository.findById(weatherDTO.getLocationId()).orElseThrow(NoLocationInDataBase::new);

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
