package java11.sda.WeatherRestApi.Weather;

import java11.sda.WeatherRestApi.Location.*;
import java11.sda.WeatherRestApi.Weather.external_api.weather_stack.WeatherJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    private final WeatherJsonService weatherJsonService;
    private final LocationService locationService;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository, LocationRepository locationRepository,
                          WeatherDTOTransformer weatherDTOTransformer, LocationDTOTransformer locationDTOTransformer,
                          WeatherJsonService weatherJsonService, LocationService locationService) {
        this.weatherRepository = weatherRepository;
        this.locationRepository = locationRepository;
        this.weatherDTOTransformer = weatherDTOTransformer;
        this.locationDTOTransformer = locationDTOTransformer;
        this.weatherJsonService = weatherJsonService;
        this.locationService = locationService;
    }


    public WeatherDTO create(WeatherDTO weatherDTO){
        Weather weather = weatherDTOTransformer.toEntity(weatherDTO);

        if (find(weather) != null) {
            throw new IllegalArgumentException();
        } else {

            String locationId = weather.getLocation().getId();
            Location location = locationRepository.findById(locationId).orElseThrow(NoSuchElementException::new);

            weather.setLocation(location);
            weatherRepository.save(weather);
            location.getWeathers().add(weather);
            locationRepository.save(location);

            return weatherDTOTransformer.toDto(weather);


        }

    }

    public List<WeatherDTO> findByDate(String date){
        return weatherRepository.findByDate(date).stream()
                .map(weatherDTOTransformer::toDto)
                .collect(Collectors.toList());
    }

    public List<WeatherDTO> findWeatherByLocation(LocationDTO locationDTO){
        return weatherRepository.findByLocation(locationDTOTransformer.toEntity(locationDTO)).stream()
                .map(weatherDTOTransformer::toDto)
                .collect(Collectors.toList());
    }

    public WeatherDTO findById(String id){

        return weatherDTOTransformer.toDto(weatherRepository.findById(id).orElseThrow(NoSuchElementException::new));

    }


    private Weather find(Weather weather){
        return weatherRepository.findByDateAndLocation(weather.getDate(),weather.getLocation());
    }

    private List<Weather> findByLocation(Location location){
        return weatherRepository.findByLocation(location);
    }

    public Weather findWeather(String cityName){

        Location location = new Location();
        location.setCityName(cityName);
        List<Location> locationList = locationService.findByName(cityName).stream().map(locationDTOTransformer::toEntity).collect(Collectors.toList());
        location = locationList.get(0);
        List<Weather> weatherList = findByLocation(location);

        Weather result;

        if (weatherList.size() == 0){
            result = weatherJsonService.getWeatherFromApi(cityName);
            result.setLocation(location);
            result = weatherRepository.save(result);
        } else {
            result = weatherList.get(0);
        }

        return result;

    }

    public List<WeatherDTO> getAll(){
        return weatherRepository.findAll().stream()
                .map(weatherDTOTransformer::toDto)
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

    public List<WeatherDTO> sortByDate(Boolean ascending){
        String sortProperties = "date";

        Sort sort = getSorter(sortProperties,ascending);

        return weatherRepository.findAll(sort).stream()
                .map(weatherDTOTransformer::toDto)
                .collect(Collectors.toList());
    }

    private Sort getSorter(String properties, boolean ascending){

        Sort.Direction sortDirection;

        if (ascending){
            sortDirection = Sort.Direction.ASC;
        } else {
            sortDirection = Sort.Direction.DESC;
        }
        return Sort.by(sortDirection,properties);
    }


}
