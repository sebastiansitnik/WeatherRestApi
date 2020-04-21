package java11.sda.WeatherRestApi.weather;

import java11.sda.WeatherRestApi.location.*;
import java11.sda.WeatherRestApi.sorter.Sorter;
import java11.sda.WeatherRestApi.sorter.SortingProperties;
import java11.sda.WeatherRestApi.external_apis.ExternalApisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class WeatherService {


    private final WeatherRepository weatherRepository;
    private final WeatherDTOTransformer weatherDTOTransformer;
    private final LocationDTOTransformer locationDTOTransformer;
    private final LocationService locationService;
    private final ExternalApisService externalApisService;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository,
                          WeatherDTOTransformer weatherDTOTransformer, LocationDTOTransformer locationDTOTransformer,
                          LocationService locationService, ExternalApisService externalApisService) {
        this.weatherRepository = weatherRepository;
        this.weatherDTOTransformer = weatherDTOTransformer;
        this.locationDTOTransformer = locationDTOTransformer;
        this.locationService = locationService;
        this.externalApisService = externalApisService;
    }


    public WeatherDTO create(WeatherDTO weatherDTO) {
        Weather weather = weatherDTOTransformer.toEntity(weatherDTO);

        if (findByLocationAndDate(weather) != null) {
            throw new WeatherAlreadyCreatedException();
        } else {

            String locationId = weather.getLocation().getId();

            Location location = locationDTOTransformer.toEntity(locationService.findById(locationId));

            weather.setLocation(location);
            weather = weatherRepository.save(weather);

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

    public List<WeatherDTO> findWeatherByLocationName(String locationName){

        return weatherRepository.findByLocationCityName(locationName).stream().map(weatherDTOTransformer::toDto).collect(Collectors.toList());

    }

    public WeatherDTO findById(String id) {
        return weatherDTOTransformer.toDto(weatherRepository.findById(id).orElseThrow(NoWeatherWithID::new));
    }


    private Weather findByLocationAndDate(Weather weather) {
        List<Weather> weathers = weatherRepository.findByDateAndLocation(weather.getDate(), weather.getLocation());

        if (weathers.size() == 0){
            weather = null;
        } else {
            weather = weathers.get(0);
        }

        return weather;
    }

    private List<Weather> findByLocation(Location location) {
        return weatherRepository.findByLocation(location);
    }

    public WeatherDTO addWeatherFromExternalApi(String cityName) {

        Location location = new Location();
        location.setCityName(cityName);
        List<Location> locationList = locationService.findByName(cityName).stream().map(locationDTOTransformer::toEntity).collect(Collectors.toList());
        location = locationList.get(0);
        if (location == null){
            location = externalApisService.getLocationFromExternalApi(cityName);
            if (location == null){
                throw new NoLocationInDataBase();
            }
        }
        List<Weather> weatherList = findByLocation(location);

        Weather result;

        result = externalApisService.getWeatherFromExternalApis(cityName);
        result.setLocation(location);

        if (weatherList.size() != 0) {

            for (Weather w: weatherList) {
                if (w.getDate().equals(result.getDate())){
                    throw new WeatherAlreadyCreatedException();
                }
            }

        }

        weatherList.add(result);
        location.setWeathers(weatherList);
        locationService.update(locationDTOTransformer.toDto(location));
        result = weatherRepository.save(result);



        return weatherDTOTransformer.toDto(result);


    }

    public List<WeatherDTO> getAll() {
        return weatherRepository.findAll().stream()
                .map(weatherDTOTransformer::toDto)
                .collect(Collectors.toList());
    }

    public WeatherDTO update(WeatherDTO weatherDTO) {
        Weather weather = weatherDTOTransformer.toEntity(weatherDTO);

        findById(weather.getId());

        weather = weatherRepository.save(weather);

        return weatherDTOTransformer.toDto(weather);

    }

    public WeatherDTO delete(String id) {

        WeatherDTO weatherDTO = findById(id);
        Weather weather = weatherDTOTransformer.toEntity(weatherDTO);

        weatherRepository.delete(weather);

        return weatherDTOTransformer.toDto(weather);

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
