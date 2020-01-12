package java11.sda.WeatherRestApi.Location;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    Location create(Location location){
        boolean isTaken = locationTaken(location);

        if (isTaken){
            throw new IllegalArgumentException();
        } else {
             return locationRepository.save(location);
        }
    }

    Location update(Location location){
        boolean isTaken = locationTaken(location);

        if (isTaken){
            return locationRepository.save(location);
        } else {
            throw new NoSuchElementException();
        }
    }

    Location delete(String id){

        Location locationForDelete = findById(id);
        if (locationForDelete == null){
            throw new NoSuchElementException();
        } else {
            locationRepository.delete(locationForDelete);
        }

        return locationForDelete;
    }

    List<Location> readAll(){
        return locationRepository.findAll();
    }

    private boolean locationTaken(Location location){
        return locationRepository.findByLatitudeAndLongitudeAndCityName(location.getLatitude(),
                location.getLongitude(),
                location.getCityName())
                != null;
    }

    public Location findById(String id){
        return locationRepository.findById(id);
    }

    public Location findByLatitudeAndLongitude(float latitude, float longitude){
        Location location = locationRepository.findByLatitudeAndLongitude(latitude,longitude);

        if (location == null){
            throw new NoSuchElementException();
        } else {
            return location;
        }
    }



}
