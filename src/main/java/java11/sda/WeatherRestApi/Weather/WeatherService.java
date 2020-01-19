package java11.sda.WeatherRestApi.Weather;

import java11.sda.WeatherRestApi.Location.Location;
import java11.sda.WeatherRestApi.Location.LocationDTO;
import java11.sda.WeatherRestApi.Location.LocationDTOTransformer;
import java11.sda.WeatherRestApi.Location.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class WeatherService {


    private final WeatherRepository weatherRepository;
    private final LocationRepository locationRepository;
    private final WeatherDTOTransformer weatherDTOTransformer;
    private final LocationDTOTransformer locationDTOTransformer;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository, LocationRepository locationRepository,
                          WeatherDTOTransformer weatherDTOTransformer, LocationDTOTransformer locationDTOTransformer) {
        this.weatherRepository = weatherRepository;
        this.locationRepository = locationRepository;
        this.weatherDTOTransformer = weatherDTOTransformer;
        this.locationDTOTransformer = locationDTOTransformer;
    }


    public WeatherDTO create(WeatherDTO weatherDTO){
        Weather weather = weatherDTOTransformer.toEntity(weatherDTO);

        if (find(weather) != null) {
            throw new IllegalArgumentException();
        } else {

            String locationId = weather.getLocation().getId();
            Location location = locationRepository.findById(locationId).get();

            weather.setLocation(location);
            weatherRepository.save(weather);
            location.getWeathers().add(weather);
            locationRepository.save(location);

            return weatherDTOTransformer.toDto(weather);


        }

    }

    public List<WeatherDTO> findWeatherByLocation(LocationDTO locationDTO){
        return weatherRepository.findByLocation(locationDTOTransformer.toEntity(locationDTO)).stream().map(weatherDTOTransformer::toDto).collect(Collectors.toList());
    }

    private Weather find(Weather weather){

        return weatherRepository.findByDateAndLocation(weather.getDate(),weather.getLocation());
    }

    public List<WeatherDTO> getAll(){
        return weatherRepository.findAll().stream().map(weatherDTOTransformer::toDto)
                .collect(Collectors.toList());
    }

    public WeatherDTO update(WeatherDTO weatherDTO){
        Weather weather = weatherDTOTransformer.toEntity(weatherDTO);
        if (weatherRepository.findById(weather.getId()).isPresent()){
            throw new NoSuchElementException();
        } else {

            weatherRepository.save(weather);
            return weatherDTOTransformer.toDto(weather);
        }

    }

    public WeatherDTO delete(String id){
        if (weatherRepository.findById(id).isPresent()){
            throw new NoSuchElementException();
        } else {
            Weather weather = weatherRepository.findById(id).get();
            weatherRepository.delete(weather);
            return weatherDTOTransformer.toDto(weather);
        }

    }


}
