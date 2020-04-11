package java11.sda.WeatherRestApi.location;

import java11.sda.WeatherRestApi.sorter.Sorter;
import java11.sda.WeatherRestApi.sorter.SortingProperties;
import java11.sda.WeatherRestApi.external_apis.ExternalApisService;
import java11.sda.WeatherRestApi.weather.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public List<LocationDTO> findByLatitude(float latitude){

        String latitudeInString = changeFloatToString(latitude);

        return locationRepository.findByLatitude(latitudeInString).stream().map(locationDTOTransformer::toDto).collect(Collectors.toList());
    }

    public LocationDTO createLocationManually(LocationDTO locationDTO) {
        Location location = locationDTOTransformer.toEntity(locationDTO);
        boolean isTaken = locationTaken(location);


        if (isTaken) {
            throw new LocationAlreadyTakenException();
        } else {
            Location locationAdded = locationRepository.save(location);
            return locationDTOTransformer.toDto(locationAdded);
        }
    }

    public LocationDTO update(LocationDTO locationDTO) {
        Location location = locationDTOTransformer.toEntity(locationDTO);

        boolean isTaken = locationTaken(location);

        if (isTaken) {
            locationRepository.save(location);
            return locationDTOTransformer.toDto(location);
        } else {
            throw new NoSuchElementException();
        }
    }

    public LocationDTO delete(String id) {

        LocationDTO locationForDelete = findById(id);;

        List<Weather> locationWeathers = weatherRepository.findByLocation(locationDTOTransformer.toEntity(locationForDelete));
        locationWeathers.forEach(weatherRepository::delete);

        locationRepository.delete(locationDTOTransformer.toEntity(locationForDelete));

        return locationForDelete;
    }

    public List<LocationDTO> readAll() {
        return locationRepository.findAll().stream().map(locationDTOTransformer::toDto).collect(Collectors.toList());
    }

    private boolean locationTaken(Location location) {
        return locationRepository.findByLatitudeAndLongitudeAndCityName(location.getLatitude(),
                location.getLongitude(),
                location.getCityName())
                != null;
    }

    public LocationDTO findById(String id) {
        return locationDTOTransformer.toDto(locationRepository.getOne(id));
    }

    private String changeFloatToString(float before){
        String result = String.format("%.3f", before);
        result = result.replace(",",".");
        return result;
    }

    public List<LocationDTO> findByLongitude(float longitude){
        String longitudeInString = changeFloatToString(longitude);

        return locationRepository.findByLongitude(longitudeInString).stream().map(locationDTOTransformer::toDto).collect(Collectors.toList());
    }

    public LocationDTO findByLatitudeAndLongitude(float latitude, float longitude) {
        String latitudeInString = changeFloatToString(latitude);
        String longitudeInString = changeFloatToString(longitude);

        Location location = locationRepository.findByLatitudeAndLongitude(latitudeInString, longitudeInString);

        if (location == null) {
            throw new NoSuchElementException();
        } else {
            return locationDTOTransformer.toDto(location);
        }
    }

    public LocationDTO createLocationAutomatically(LocationDTO locationDTO) {
        Location location = externalApisService.getLocationFromExternalApi(locationDTO.getCityName());
        if (location == null){
            throw new NoSuchElementException("We could not find that location.");
        } else {
            if (locationTaken(location)){
                throw new LocationAlreadyTakenException();
            } else {
                location = locationRepository.save(location);
            }
        }

        return locationDTOTransformer.toDto(location);
    }

    public List<LocationDTO> findByName(String placeName) {

        List<LocationDTO> result = locationRepository.findByCityName(placeName).stream()
                .map(locationDTOTransformer::toDto)
                .collect(Collectors.toList());

        if (result.isEmpty()) {

            LocationDTO locationDTO = new LocationDTO();
            locationDTO.setCityName(placeName);

            LocationDTO newLocationDTO = createLocationAutomatically(locationDTO);
            result.add(newLocationDTO);

        }
        return result;
    }

    public List<LocationDTO> findByRegion(String region) {

        return locationRepository.findByRegion(region).stream().map(locationDTOTransformer::toDto).collect(Collectors.toList());
    }

    public List<LocationDTO> findByCountry(String country) {
        return locationRepository.findByCountry(country).stream().map(locationDTOTransformer::toDto).collect(Collectors.toList());
    }

    public List<LocationDTO> sortByCityName(boolean ascending) {

        Sorter sorter = new Sorter();
        String sortProperties = SortingProperties.SORTING_BY_CITY_NAME.property;
        Sort sort = sorter.setupSort(sortProperties, ascending);

        return locationRepository.findAll(sort).stream().map(locationDTOTransformer::toDto).collect(Collectors.toList());
    }


}
