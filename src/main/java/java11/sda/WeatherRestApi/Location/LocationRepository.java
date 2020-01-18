package java11.sda.WeatherRestApi.Location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location,Integer> {

    List<Location> findByCityName(String cityName);
    Location findById(String id);
    List<Location> findByRegion(String region);
    List<Location> findByCountry(String country);

    Location findByLatitudeAndLongitudeAndCityName(float latitude, float longitude, String cityName);
    Location findByLatitudeAndLongitude(float latitude, float longitude);









}
