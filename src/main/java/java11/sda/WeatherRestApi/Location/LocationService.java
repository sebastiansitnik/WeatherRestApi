package java11.sda.WeatherRestApi.location;

import java11.sda.WeatherRestApi.sorter.Sorter;
import java11.sda.WeatherRestApi.sorter.SortingProperties;
import java11.sda.WeatherRestApi.external_apis.ExternalApisService;
import java11.sda.WeatherRestApi.weather.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationDTOTransformer locationDTOTransformer;
    private final ExternalApisService externalApisService;
    private final WeatherRepository weatherRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository, LocationDTOTransformer locationDTOTransformer, ExternalApisService externalApisService,
                           WeatherRepository weatherRepository) {
        this.locationRepository = locationRepository;
        this.locationDTOTransformer = locationDTOTransformer;
        this.externalApisService = externalApisService;
        this.weatherRepository = weatherRepository;
    }

    private List<Location> getLocationByLatitudeFromRepository(String latitude){
        return locationRepository.findByLatitude(latitude);
    }

    public List<LocationDTO> findByLatitude(float latitude){

        String latitudeInString = changeFloatToString(latitude);

        return getLocationByLatitudeFromRepository(latitudeInString).stream()
                .map(this::transformToDto)
                .collect(Collectors.toList());
    }

    private LocationDTO transformToDto(Location location){
        return locationDTOTransformer.toDto(location);
    }

    private Location transformToEntity(LocationDTO locationDTO){
        return locationDTOTransformer.toEntity(locationDTO);
    }

    public LocationDTO createLocationManually(LocationDTO locationDTO) {

        Location location = transformToEntity(locationDTO);

        Location result = checkIfLocationIsTaken(location);

        return transformToDto(addLocation(result));

    }

    private Location addLocation(Location location){
        return locationRepository.save(location);
    }

    private LocationDTO updateLocationDTO(LocationDTO oldLocationDTO,LocationDTO newLocationDTO ){
        oldLocationDTO.setCountry(newLocationDTO.getCountry());
        oldLocationDTO.setRegion(newLocationDTO.getRegion());
        oldLocationDTO.setCityName(newLocationDTO.getCityName());
        oldLocationDTO.setLongitude(newLocationDTO.getLongitude());
        oldLocationDTO.setLatitude(newLocationDTO.getLatitude());

        return oldLocationDTO;
    }

    public LocationDTO update(LocationDTO locationDTO) {
        LocationDTO oldLocationDTO = findById(locationDTO.getId());

        LocationDTO locationDTOAfterChange = updateLocationDTO(oldLocationDTO, locationDTO);

        return transformToDto(addLocation(transformToEntity(locationDTOAfterChange)));
    }

    private void deleteWeathersAndLocation(Location location){
        deleteWeathersInLocationFromRepository(location.getWeathers());
        deleteLocationFromRepository(location);
    }

    private void deleteWeathersInLocationFromRepository(List<Weather> weathers){
        weathers.forEach(weatherRepository::delete);
    }

    private void deleteLocationFromRepository(Location location){
        locationRepository.delete(location);
    }

    public LocationDTO delete(String id) {

        LocationDTO locationForDelete = findById(id);

        deleteWeathersAndLocation(transformToEntity(locationForDelete));

        return locationForDelete;
    }

    public List<LocationDTO> readAll() {
        return locationRepository.findAll().stream()
                .map(this::transformToDto)
                .collect(Collectors.toList());
    }


    private Location findByLatitudeAndLongitudeAndCityName(Location location){
        return locationRepository.findByLatitudeAndLongitudeAndCityName(location.getLatitude(),
                location.getLongitude(),
                location.getCityName());
    }

    private Location checkIfLocationIsTaken(Location location) {

        Location result = findByLatitudeAndLongitudeAndCityName(location);

        if (result == null) {
            return location;
        } else {
            throw new LocationAlreadyTakenException();
        }
    }

    public LocationDTO findById(String id) {
        return transformToDto(locationRepository.getOne(id));
    }

    private String formatForFloats(){return "%.3f";}
    private String commaForReplacement(){return ",";}
    private String dotForReplacement(){return ".";}

    private String changeFloatToString(float before){
        String result = String.format(formatForFloats(), before);
        result = result.replace(commaForReplacement(),dotForReplacement());
        return result;
    }

    public List<LocationDTO> findByLongitude(float longitude){
        String longitudeInString = changeFloatToString(longitude);

        return locationRepository.findByLongitude(longitudeInString).stream()
                .map(this::transformToDto)
                .collect(Collectors.toList());
    }

    private Location findLocationInRepositoryByLatitudeAndLongitude(String latitude, String longitude){
        return locationRepository.findByLatitudeAndLongitude(latitude, longitude);
    }

    public LocationDTO findByLatitudeAndLongitude(float latitude, float longitude) {
        String latitudeInString = changeFloatToString(latitude);
        String longitudeInString = changeFloatToString(longitude);

        Location location = findLocationInRepositoryByLatitudeAndLongitude(latitudeInString,longitudeInString);

        if (location == null) {
            throw new NoSuchElementException();
        } else {
            return locationDTOTransformer.toDto(location);
        }
    }

    private String messageForNoSuchElementException(){return "We could not find that location.";}

    private Location getLocationFromExternalApi(String cityName){
        return externalApisService.getLocationFromExternalApi(cityName);
    }


    public LocationDTO createLocationAutomatically(LocationDTO locationDTO) {
        Location location = getLocationFromExternalApi(locationDTO.getCityName());

        if (location == null){
            throw new NoSuchElementException(messageForNoSuchElementException());
        } else {

            Location result = checkIfLocationIsTaken(location);

            return transformToDto(addLocation(result));
            }

    }

    private List<Location> getLocationsByNameFromRepository(String locationName){
        return locationRepository.findByCityName(locationName);
    }

    public List<LocationDTO> findByName(String placeName) {

        List<Location> result = getLocationsByNameFromRepository(placeName);

        return result.stream()
                .map(this::transformToDto)
                .collect(Collectors.toList());
    }

    private List<Location> getLocationsByRegionFromRepository(String region){
        return locationRepository.findByRegion(region);
    }

    public List<LocationDTO> findByRegion(String region) {

        return getLocationsByRegionFromRepository(region).stream()
                .map(this::transformToDto)
                .collect(Collectors.toList());
    }

    public List<LocationDTO> findByCountry(String country) {
        return locationRepository.findByCountry(country).stream().map(locationDTOTransformer::toDto).collect(Collectors.toList());
    }

    private List<Location> getLocationsSortedByLocationNameFromRepository(Sort sort){
        return locationRepository.findAll(sort);
    }

    public List<LocationDTO> sortByCityName(boolean ascending) {

        Sorter sorter = new Sorter();
        String sortProperties = SortingProperties.SORTING_BY_CITY_NAME.property;
        Sort sort = sorter.setupSort(sortProperties, ascending);

        return getLocationsSortedByLocationNameFromRepository(sort).stream()
                .map(this::transformToDto)
                .collect(Collectors.toList());
    }


}
