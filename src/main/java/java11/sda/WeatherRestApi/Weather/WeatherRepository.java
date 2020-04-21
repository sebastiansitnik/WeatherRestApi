package java11.sda.WeatherRestApi.weather;

import java11.sda.WeatherRestApi.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WeatherRepository extends JpaRepository<Weather, String> {

    List<Weather> findByDateAndLocation(String date, Location location);

    List<Weather> findByLocation(Location location);

    List<Weather> findByDate(String date);

    List<Weather> findByLocationCityName(String locationName);


}
