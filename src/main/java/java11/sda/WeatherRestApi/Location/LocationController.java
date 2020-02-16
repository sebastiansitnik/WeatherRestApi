package java11.sda.WeatherRestApi.location;

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
    public LocationDTO createLocation(@RequestBody LocationDTO location) {
        return locationService.createLocationManually(location);
    }

    @GetMapping
    public List<LocationDTO> readAllLocations() {
        return locationService.readAll();
    }

    @PutMapping
    public LocationDTO updateLocation(@RequestBody LocationDTO location) {
        return locationService.update(location);
    }

    @DeleteMapping
    public LocationDTO deleteLocation(@RequestParam String id) {
        return locationService.delete(id);
    }

    @GetMapping("/findByCoordinates")
    public LocationDTO findByLatitudeAndLongitude(@RequestParam long latitude, long longitude) {
        return locationService.findByLatitudeAndLongitude(latitude, longitude);
    }

    @GetMapping("/findByCityName")
    public List<LocationDTO> findByCityName(@RequestParam String cityName) {
        return locationService.findByName(cityName);
    }

    @GetMapping("/findByRegion")
    public List<LocationDTO> findByRegion(@RequestParam String region) {
        return locationService.findByRegion(region);
    }

    @GetMapping("/findByCountry")
    public List<LocationDTO> findByCountry(@RequestParam String country) {
        return locationService.findByCountry(country);
    }

    @GetMapping("/{id}")
    public LocationDTO findById(@PathVariable String id) {
        return locationService.findById(id);
    }

    @GetMapping("/sortByCityName")
    public List<LocationDTO> sortByCityName(@RequestParam(required = false, defaultValue = "true") boolean ascending) {
        return locationService.sortByCityName(ascending);
    }


}
