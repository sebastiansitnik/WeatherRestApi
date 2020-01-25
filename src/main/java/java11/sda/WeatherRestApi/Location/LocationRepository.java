package java11.sda.WeatherRestApi.Location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location,String> {

    List<Location> findByCityName(String cityName);
    List<Location> findByRegion(String region);
    List<Location> findByCountry(String country);

    Location findByLatitudeAndLongitudeAndCityName(float latitude, float longitude, String cityName);
    Location findByLatitudeAndLongitude(float latitude, float longitude);









}
