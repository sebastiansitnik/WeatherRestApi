package java11.sda.WeatherRestApi.location;

import java11.sda.WeatherRestApi.weather.WeatherDTOTransformer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class LocationDTOTransformer {

    private final WeatherDTOTransformer weatherDTOTransformer;

    public LocationDTOTransformer(WeatherDTOTransformer weatherDTOTransformer) {
        this.weatherDTOTransformer = weatherDTOTransformer;
    }

    public Location toEntity(LocationDTO locationDTO) {
        Location location = new Location();

        location.setLatitude(changeFloatToString(locationDTO.getLatitude()));
        location.setLongitude(changeFloatToString(locationDTO.getLongitude()));
        location.setCityName(locationDTO.getCityName());
        location.setCountry(locationDTO.getCountry());
        location.setRegion(locationDTO.getRegion());
        location.setWeathers(locationDTO.getWeathers().stream().map(weatherDTOTransformer::toEntity).collect(Collectors.toList()));
        location.setId(locationDTO.getId());

        return location;
    }

    private String changeFloatToString(float before){
        String result = String.format("%.3f", before);
        result = result.replace(",",".");
        return result;
    }


    public LocationDTO toDto(Location location) {
        LocationDTO locationDTO = new LocationDTO();

        locationDTO.setId(location.getId());
        locationDTO.setLongitude(Float.parseFloat(location.getLongitude()));
        locationDTO.setLatitude(Float.parseFloat(location.getLatitude()));
        locationDTO.setCityName(location.getCityName());
        locationDTO.setRegion(location.getRegion());
        locationDTO.setCountry(location.getCountry());
        locationDTO.setWeathers(location.getWeathers().stream().map(weatherDTOTransformer::toDto).collect(Collectors.toList()));

        return locationDTO;
    }


}
