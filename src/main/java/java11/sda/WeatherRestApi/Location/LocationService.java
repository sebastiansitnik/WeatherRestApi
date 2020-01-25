package java11.sda.WeatherRestApi.Location;

import java11.sda.WeatherRestApi.Location.external_api.weather_stack.LocationJsonService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationDTOTransformer locationDTOTransformer;
    private final LocationJsonService locationJsonService;

    public LocationService(LocationRepository locationRepository, LocationDTOTransformer locationDTOTransformer, LocationJsonService locationJsonService) {
        this.locationRepository = locationRepository;
        this.locationDTOTransformer = locationDTOTransformer;
        this.locationJsonService = locationJsonService;
    }

    LocationDTO create(LocationDTO locationDTO){
        Location location = locationDTOTransformer.toEntity(locationDTO);
        boolean isTaken = locationTaken(location);

        if (isTaken){
            throw new IllegalArgumentException();
        } else {
            locationRepository.save(location);
            return locationDTOTransformer.toDto(location);
        }
    }

    LocationDTO update(LocationDTO locationDTO){
        Location location = new Location();

        boolean isTaken = locationTaken(location);

        if (isTaken){
            locationRepository.save(location);
            return locationDTOTransformer.toDto(location);
        } else {
            throw new NoSuchElementException();
        }
    }

    LocationDTO delete(String id){

        LocationDTO locationForDelete = findById(id);
        if (locationForDelete == null){
            throw new NoSuchElementException();
        } else {
            locationRepository.delete(locationDTOTransformer.toEntity(locationForDelete));
        }

        return locationForDelete;
    }

    public List<LocationDTO> readAll(){
        return locationRepository.findAll().stream().map(locationDTOTransformer::toDto).collect(Collectors.toList());
    }

    private boolean locationTaken(Location location){
        return locationRepository.findByLatitudeAndLongitudeAndCityName(location.getLatitude(),
                location.getLongitude(),
                location.getCityName())
                != null;
    }

    public LocationDTO findById(String id){
        return locationDTOTransformer.toDto(locationRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public LocationDTO findByLatitudeAndLongitude(float latitude, float longitude){
        Location location = locationRepository.findByLatitudeAndLongitude(latitude,longitude);

        if (location == null){
            throw new NoSuchElementException();
        } else {
            return locationDTOTransformer.toDto(location);
        }
    }

    private Location createNewLocationFromExternalApi(String cityName){
        Location location = locationJsonService.getLocationFromExternalApi(cityName);

        if (location != null){
            location = locationRepository.save(location);
        } else {
            throw new NoSuchElementException();
        }

        return location;
    }

    public List<LocationDTO> findByName(String placeName){

        List<LocationDTO> result = locationRepository.findByCityName(placeName).stream()
                .map(locationDTOTransformer::toDto)
                .collect(Collectors.toList());

        if (result.isEmpty()){

            Location location = createNewLocationFromExternalApi(placeName);
            LocationDTO locationDTO = locationDTOTransformer.toDto(location);
            result.add(locationDTO);

        }
        return result;
    }

    public List<LocationDTO> findByRegion(String region){
        return locationRepository.findByRegion(region).stream().map(locationDTOTransformer::toDto).collect(Collectors.toList());
    }

    public List<LocationDTO> findByCountry(String country){
        return locationRepository.findByCountry(country).stream().map(locationDTOTransformer::toDto).collect(Collectors.toList());
    }

    public List<LocationDTO> sortByCityName(boolean ascending){
        Sort sort;
        Sort.Direction sortDirection;
        String sortProperties = "cityName";
        if (ascending){
            sortDirection = Sort.Direction.ASC;
        }else {
            sortDirection = Sort.Direction.DESC;
        }
        sort = Sort.by(sortDirection,sortProperties);

        return locationRepository.findAll(sort).stream().map(locationDTOTransformer::toDto).collect(Collectors.toList());
    }







}
