package java11.sda.WeatherRestApi.weather;

import java11.sda.WeatherRestApi.location.*;
import java11.sda.WeatherRestApi.sorter.Sorter;
import java11.sda.WeatherRestApi.sorter.SortingProperties;
import java11.sda.WeatherRestApi.external_apis.ExternalApisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherService {


    private final WeatherRepository weatherRepository;
    private final WeatherDTOTransformer weatherDTOTransformer;
    private final LocationDTOTransformer locationDTOTransformer;
    private final ExternalApisService externalApisService;
    private final LocationRepository locationRepository;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository,
                          WeatherDTOTransformer weatherDTOTransformer, LocationDTOTransformer locationDTOTransformer,
                          ExternalApisService externalApisService, LocationRepository locationRepository) {
        this.weatherRepository = weatherRepository;
        this.weatherDTOTransformer = weatherDTOTransformer;
        this.locationDTOTransformer = locationDTOTransformer;
        this.externalApisService = externalApisService;
        this.locationRepository = locationRepository;
    }

    private void checkIfWeatherAlreadyExistInRepository(Weather weather) {

        if (findByLocationAndDate(weather) != null) {
            throw new WeatherAlreadyCreatedException();
        }
    }

    private String getLocationIDFromWeather(Weather weather){
        return weather.getLocation().getId();
    }

    private WeatherDTO transformWeatherToDto(Weather weather){
        return weatherDTOTransformer.toDto(weather);
    }

    private Weather transformDtoToWeather(WeatherDTO weatherDTO){
        return weatherDTOTransformer.toEntity(weatherDTO);
    }

    public WeatherDTO create(WeatherDTO weatherDTO) {
        Weather weather = transformDtoToWeather(weatherDTO);

        checkIfWeatherAlreadyExistInRepository(weather);

        String locationId = getLocationIDFromWeather(weather);

        Location location = locationRepository.findById(locationId).orElseThrow(NoLocationInDataBase::new);

        weather.setLocation(location);

        return transformWeatherToDto(addWeatherToRepository(weather));

    }

    private Weather addWeatherToRepository(Weather weather){
        return weatherRepository.save(weather);
    }

    private List<Weather> getWeatherByDateFromRepository(String date){
        return weatherRepository.findByDate(date);
    }

    public List<WeatherDTO> findByDate(String date) {

        return getWeatherByDateFromRepository(date).stream()
                .map(this::transformWeatherToDto)
                .collect(Collectors.toList());
    }

    private List<Weather> getWeatherByLocationFromRepository(Location location){
        return weatherRepository.findByLocation(location);
    }

    private Location transformDtoToLocation(LocationDTO locationDTO){
        return locationDTOTransformer.toEntity(locationDTO);
    }

    public List<WeatherDTO> findWeatherByLocation(LocationDTO locationDTO) {
        locationDTO.setWeathers(new ArrayList<>());

        Location location = transformDtoToLocation(locationDTO);

        return getWeatherByLocationFromRepository(location).stream()
                .map(this::transformWeatherToDto)
                .collect(Collectors.toList());
    }

    private List<Weather> getWeatherByLocationName(String locationName){
        return weatherRepository.findByLocationCityName(locationName);
    }

    public List<WeatherDTO> findWeatherByLocationName(String locationName){
        return getWeatherByLocationName(locationName).stream()
                .map(this::transformWeatherToDto)
                .collect(Collectors.toList());

    }

    public WeatherDTO findById(String id) {
        return weatherDTOTransformer.toDto(weatherRepository.findById(id).orElseThrow(NoWeatherWithID::new));
    }


    private Weather findByLocationAndDate(Weather weather) {
        return weatherRepository.findByDateAndLocation(weather.getDate(),weather.getLocation()).stream()
                .findFirst()
                .orElse(null);
    }

    private Location getLocationByNameFromRepository(String locationName){
        return locationRepository.findByCityName(locationName).stream()
                .findFirst().orElse(null);
    }

    private Location addLocationToRepository(Location location){
        return locationRepository.save(location);
    }

    private Location createLocationForWeatherIfNotExist(Location location){
        Location result = getLocationByNameFromRepository(location.getCityName());

        if (result == null){
            result = addLocationToRepository(location);
        }

        return result;
    }

    public WeatherDTO addWeatherFromExternalApi(String cityName) {

        Weather weather = externalApisService.getWeatherFromExternalApis(cityName);

        Location location = createLocationForWeatherIfNotExist(weather.getLocation());

        List<Weather> weathersAlreadyInLocation = location.getWeathers();

        if (weathersAlreadyInLocation.size() != 0) {

            for (Weather w: weathersAlreadyInLocation) {
                if (w.getDate().equals(weather.getDate())){
                    throw new WeatherAlreadyCreatedException();
                }
            }

        }

        weather.setLocation(location);
        weather = addWeatherToRepository(weather);

        return weatherDTOTransformer.toDto(weather);


    }

    public List<WeatherDTO> getAll() {
        return weatherRepository.findAll().stream()
                .map(weatherDTOTransformer::toDto)
                .collect(Collectors.toList());
    }

    public WeatherDTO update(WeatherDTO weatherDTO) {
        Weather weather = weatherDTOTransformer.toEntity(weatherDTO);

        findById(weather.getId());

        weather = addWeatherToRepository(weather);

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
