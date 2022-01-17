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
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public LocationDTO createLocation(@RequestBody LocationDTO locationDTO) {
        return locationService.createLocationManually(locationDTO);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/addByExternalApi")
    public LocationDTO addLocationByExternalApi(@RequestBody LocationDTO locationDTO){
        return locationService.createLocationAutomatically(locationDTO);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<LocationDTO> readAllLocations() {
        return locationService.readAll();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping
    public LocationDTO updateLocation(@RequestBody LocationDTO locationDTO) {
        return locationService.update(locationDTO);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping
    public LocationDTO deleteLocation(@RequestParam String id) {
        return locationService.delete(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/findByCoordinates")
    public LocationDTO findByLatitudeAndLongitude(@RequestParam float latitude, float longitude) {
        return locationService.findByLatitudeAndLongitude(latitude, longitude);
    }

    @CrossOrigin
    @GetMapping("/findByCityName")
    public List<LocationDTO> findByCityName(@RequestParam String cityName) {
        return locationService.findByName(cityName);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/findByRegion")
    public List<LocationDTO> findByRegion(@RequestParam String region) {
        return locationService.findByRegion(region);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/findByCountry")
    public List<LocationDTO> findByCountry(@RequestParam String country) {
        return locationService.findByCountry(country);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/findByLatitude")
    public List<LocationDTO> findByLatitude(@RequestParam float latitude){
        return locationService.findByLatitude(latitude);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/findByLongitude")
    public List<LocationDTO> findByLongitude(@RequestParam float longitude){
        return locationService.findByLongitude(longitude);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public LocationDTO findById(@PathVariable String id) {
        return locationService.findById(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/sortByCityName")
    public List<LocationDTO> sortByCityName(@RequestParam(required = false, defaultValue = "true") boolean ascending) {
        return locationService.sortByCityName(ascending);
    }


}
