package java11.sda.WeatherRestApi.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LocationRepository extends JpaRepository<Location, String> {

    List<Location> findByCityName(String cityName);

    List<Location> findByRegion(String region);

    List<Location> findByCountry(String country);

    Location findByLatitudeAndLongitudeAndCityName(String latitude, String longitude, String cityName);
    List<Location> findByLatitude(String latitude);
    List<Location> findByLongitude(String longitude);


    Location findByLatitudeAndLongitude(String latitude, String longitude);


}
