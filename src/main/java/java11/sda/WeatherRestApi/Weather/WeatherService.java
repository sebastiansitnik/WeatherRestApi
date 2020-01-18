package java11.sda.WeatherRestApi.Weather;

import java11.sda.WeatherRestApi.Location.Location;
import java11.sda.WeatherRestApi.Location.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WeatherService {


    private final WeatherRepository weatherRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository, LocationRepository locationRepository) {
        this.weatherRepository = weatherRepository;
        this.locationRepository = locationRepository;
    }


    public Weather create(Weather weather){
        if (find(weather) != null) {
            throw new IllegalArgumentException();
        } else {

        String locationId = weather.getLocation().getId();
        Location location = locationRepository.findById(locationId);

        weather.setLocation(location);
        Weather savedWeather = weatherRepository.save(weather);
        location.getWeathers().add(savedWeather);
        locationRepository.save(location);
        return savedWeather;


        }

    }

    private Weather find(Weather weather){
        return weatherRepository.findByDateAndLocation(weather.getDate(),weather.getLocation());
    }

    public List<Weather> getAll(){
        return weatherRepository.findAll();
    }

    public Weather update(Weather weather){
        if (weatherRepository.findById(weather.getId()) == null){
            throw new NoSuchElementException();
        } else {
            return weatherRepository.save(weather);
        }

    }

    public Weather delete(String id){
        if (weatherRepository.findById(id) == null){
            throw new NoSuchElementException();
        } else {
            Weather weather = weatherRepository.findById(id);
            weatherRepository.delete(weather);
            return weather;
        }

    }


}
