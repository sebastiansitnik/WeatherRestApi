package java11.sda.WeatherRestApi.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LocationRepository extends JpaRepository<Location, String> {

    List<Location> findByCityName(String cityName);

    List<Location> findByRegion(String region);

    List<Location> findByCountry(String country);

    Location findByLatitudeAndLongitudeAndCityName(float latitude, float longitude, String cityName);

    Location findByLatitudeAndLongitude(float latitude, float longitude);


}
