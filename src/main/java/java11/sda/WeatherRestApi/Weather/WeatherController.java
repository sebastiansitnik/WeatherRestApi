package java11.sda.WeatherRestApi.Weather;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Weather create(@RequestBody Weather weather){
        return weatherService.create(weather);
    }

    @GetMapping
    public List<Weather> getAll(){
        return weatherService.getAll();
    }

    @PutMapping
    public Weather update(@RequestBody Weather weather){
        return weatherService.update(weather);
    }

    @DeleteMapping
    public Weather delete(@RequestParam String id){
        return weatherService.delete(id);
    }


}
