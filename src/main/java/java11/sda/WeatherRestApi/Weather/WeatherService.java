package java11.sda.WeatherRestApi.Weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class WeatherService {


    private final WeatherRepository weatherRepository;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }


    public Weather create(Weather weather){
        if (find(weather) != null){
            throw new IllegalArgumentException();
        } else {
            return weatherRepository.save(weather);
        }

    }

    private Weather find(Weather weather){
        return weatherRepository.findByDateAndLocation(weather.getDate(),weather.getLocation());
    }
}
