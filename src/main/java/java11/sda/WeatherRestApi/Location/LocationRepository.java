package java11.sda.WeatherRestApi.Location;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Integer> {

    Location findByCityName(String cityName);
    Location findByLatitudeAndLongitudeAndCityName(float latitude, float longitude, String cityName);
    Location findById(String id);
    Location findByLatitudeAndLongitude(float latitude, float longitude);


}
