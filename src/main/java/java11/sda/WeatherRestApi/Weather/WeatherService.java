package java11.sda.WeatherRestApi.weather;

import java11.sda.WeatherRestApi.location.*;
import java11.sda.WeatherRestApi.sorter.Sorter;
import java11.sda.WeatherRestApi.sorter.SortingProperties;
import java11.sda.WeatherRestApi.external_apis.ExternalApisService;
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
    private final LocationService locationService;
    private final ExternalApisService externalApisService;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository, LocationRepository locationRepository,
                          WeatherDTOTransformer weatherDTOTransformer, LocationDTOTransformer locationDTOTransformer,
                          LocationService locationService, ExternalApisService externalApisService) {
        this.weatherRepository = weatherRepository;
        this.locationRepository = locationRepository;
        this.weatherDTOTransformer = weatherDTOTransformer;
        this.locationDTOTransformer = locationDTOTransformer;
        this.locationService = locationService;
        this.externalApisService = externalApisService;
    }


    public WeatherDTO create(WeatherDTO weatherDTO) {
        Weather weather = weatherDTOTransformer.toEntity(weatherDTO);

        if (find(weather) != null) {
            throw new WeatherAlreadyCreatedException();
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

    public List<WeatherDTO> findByDate(String date) {
        return weatherRepository.findByDate(date).stream()
                .map(weatherDTOTransformer::toDto)
                .collect(Collectors.toList());
    }

    public List<WeatherDTO> findWeatherByLocation(LocationDTO locationDTO) {
        return weatherRepository.findByLocation(locationDTOTransformer.toEntity(locationDTO)).stream()
                .map(weatherDTOTransformer::toDto)
                .collect(Collectors.toList());
    }

    public WeatherDTO findById(String id) {

        return weatherDTOTransformer.toDto(weatherRepository.findById(id).orElseThrow(NoSuchElementException::new));

    }


    private Weather find(Weather weather) {
        return weatherRepository.findByDateAndLocation(weather.getDate(), weather.getLocation());
    }

    private List<Weather> findByLocation(Location location) {
        return weatherRepository.findByLocation(location);
    }

    public WeatherDTO findWeather(String cityName) {

        Location location = new Location();
        location.setCityName(cityName);
        List<Location> locationList = locationService.findByName(cityName).stream().map(locationDTOTransformer::toEntity).collect(Collectors.toList());
        location = locationList.get(0);
        List<Weather> weatherList = findByLocation(location);

        Weather result;

        if (weatherList.size() == 0) {
            result = externalApisService.getWeatherFromExternalApis(cityName);
            result.setLocation(location);
            result = weatherRepository.save(result);

            weatherList.add(result);
            location.setWeathers(weatherList);
            locationService.update(locationDTOTransformer.toDto(location));
            
        } else {
            result = weatherList.get(0);
        }




        return weatherDTOTransformer.toDto(result);

    }

    public List<WeatherDTO> getAll() {
        return weatherRepository.findAll().stream()
                .map(weatherDTOTransformer::toDto)
                .collect(Collectors.toList());
    }

    public WeatherDTO update(WeatherDTO weatherDTO) {
        Weather weather = weatherDTOTransformer.toEntity(weatherDTO);
        if (weatherRepository.findById(weather.getId()).isPresent()) {
            throw new NoSuchElementException();
        } else {

            weatherRepository.save(weather);
            return weatherDTOTransformer.toDto(weather);
        }

    }

    public WeatherDTO delete(String id) {
        if (weatherRepository.findById(id).isPresent()) {
            throw new NoSuchElementException();
        } else {
            Weather weather = weatherRepository.findById(id).get();
            weatherRepository.delete(weather);
            return weatherDTOTransformer.toDto(weather);
        }

    }

    public List<WeatherDTO> sortByDate(Boolean ascending) {
        Sorter sorter = new Sorter();
        String sortProperties = SortingProperties.SORTING_BY_DATE.property;

        Sort sort = sorter.setupSort(sortProperties, ascending);

        return weatherRepository.findAll(sort).stream()
                .map(weatherDTOTransformer::toDto)
                .collect(Collectors.toList());
    }


}
