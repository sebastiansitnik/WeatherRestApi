package java11.sda.WeatherRestApi.Weather;

import java11.sda.WeatherRestApi.Location.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather,String> {

    Weather findByDateAndLocation(String date, Location location);
    List<Weather> findByLocation(Location location);

}
