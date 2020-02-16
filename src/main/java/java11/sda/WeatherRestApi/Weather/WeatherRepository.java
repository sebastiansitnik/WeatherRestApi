package java11.sda.WeatherRestApi.weather;

import java11.sda.WeatherRestApi.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, String> {

    Weather findByDateAndLocation(String date, Location location);

    List<Weather> findByLocation(Location location);

    List<Weather> findByDate(String date);


}
