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
    public LocationDTO createLocation(@RequestBody LocationDTO location){
        return locationService.create(location);
    }

    @GetMapping
    public List<LocationDTO> readAllLocations(){
        return locationService.readAll();
    }

    @PutMapping
    public LocationDTO updateLocation(@RequestBody LocationDTO location){
        return locationService.update(location);
    }

    @DeleteMapping
    public LocationDTO deleteLocation(@RequestParam String id){
        return locationService.delete(id);
    }

    @GetMapping("/find")
    public LocationDTO findByParams(@RequestParam (required = false) String id, float latitude, float longitude, String cityName, String region, String country){
        return findByParams(id,latitude,longitude,cityName,region,country);
    }




}
