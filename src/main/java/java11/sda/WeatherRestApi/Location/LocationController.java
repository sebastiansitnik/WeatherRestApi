package java11.sda.WeatherRestApi.Location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public Location createLocation(@RequestBody Location location){
        return locationService.create(location);
    }

    @GetMapping
    public List<Location> readAllLocations(){
        return locationService.readAll();
    }

    @PutMapping
    public Location updateLocation(@RequestBody Location location){
        return locationService.update(location);
    }

    @DeleteMapping
    public Location deleteLocation(@RequestParam String id){
        return locationService.delete(id);
    }

    @GetMapping("/find")
    public Location findByLatitudeAndLongitude(@RequestParam float latitude, float longitude){
        return locationService.findByLatitudeAndLongitude(latitude,longitude);
    }

}
