package java11.sda.WeatherRestApi.Location;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationDTOTransformer locationDTOTransformer;

    public LocationService(LocationRepository locationRepository, LocationDTOTransformer locationDTOTransformer) {
        this.locationRepository = locationRepository;
        this.locationDTOTransformer = locationDTOTransformer;
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

    List<LocationDTO> readAll(){
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

    public List<LocationDTO> findByName(String placeName){
        return locationRepository.findByCityName(placeName).stream().map(locationDTOTransformer::toDto).collect(Collectors.toList());
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
