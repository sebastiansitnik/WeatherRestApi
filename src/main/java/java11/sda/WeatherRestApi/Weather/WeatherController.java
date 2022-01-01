package java11.sda.WeatherRestApi.weather;

import java11.sda.WeatherRestApi.location.LocationDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public WeatherDTO create(@RequestBody @Valid WeatherDTO weather) {
        return weatherService.create(weather);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<WeatherDTO> getAll() {
        return weatherService.getAll();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping
    public WeatherDTO update(@RequestBody @Valid WeatherDTO weather) {
        return weatherService.update(weather);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping
    public WeatherDTO delete(@RequestParam String id) {
        return weatherService.delete(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/findByLocationName")
    public List<WeatherDTO> findByLocationName(@RequestParam String locationName) {
        return weatherService.findWeatherByLocationName(locationName);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/findByLocation")
    public List<WeatherDTO> findByLocation(@RequestBody LocationDTO location) {
        return weatherService.findWeatherByLocation(location);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public WeatherDTO findById(@PathVariable String id) {
        return weatherService.findById(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/FindByDate")
    public List<WeatherDTO> findByDate(@RequestParam String date) {
        return weatherService.findByDate(date);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getAllSortedByDate")
    public List<WeatherDTO> getAllSorted(@RequestParam(required = false, defaultValue = "true") boolean ascending) {
        return weatherService.sortByDate(ascending);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/addFromExternalApi")
    public WeatherDTO addFromExternalApi(@RequestParam String cityName) {
        return weatherService.addWeatherFromExternalApi(cityName);
    }
}
