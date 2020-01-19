package java11.sda.WeatherRestApi.Weather;


import java11.sda.WeatherRestApi.Location.Location;
import java11.sda.WeatherRestApi.Location.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping
    public WeatherDTO create(@RequestBody @Valid WeatherDTO weather){
        return weatherService.create(weather);
    }

    @GetMapping
    public List<WeatherDTO> getAll(){
        return weatherService.getAll();
    }

    @PutMapping
    public WeatherDTO update(@RequestBody @Valid WeatherDTO weather){
        return weatherService.update(weather);
    }

    @DeleteMapping
    public WeatherDTO delete(@RequestParam String id){
        return weatherService.delete(id);
    }

    @GetMapping("/findByLocation")
    public List<WeatherDTO> findByLocation(@RequestBody LocationDTO location){
        return weatherService.findWeatherByLocation(location);
    }

    @GetMapping("/{id}")
    public WeatherDTO findById(@PathVariable String id){
        return weatherService.findById(id);
    }

    @GetMapping("/FindByDate")
    public List<WeatherDTO> findByDate(@RequestParam String date){
        return weatherService.findByDate(date);
    }
}
