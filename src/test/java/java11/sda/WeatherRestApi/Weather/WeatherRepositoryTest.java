package java11.sda.WeatherRestApi.Weather;

import java11.sda.WeatherRestApi.Location.Location;
import java11.sda.WeatherRestApi.Location.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class WeatherRepositoryTest {

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Weather getTestWeather(){
        Weather weather = new Weather();

        Location location = new Location();
        location.setCityName("Test City");
        location.setRegion("Region Test");
        location.setCountry("Test Country");
        location.setLongitude(12);
        location.setLatitude(30);

        weather.setLocation(testEntityManager.persist(location));
        weather.setDate("02.20.2016");
        weather.setWindSpeed(123);
        weather.setWindDirection("S");
        weather.setHumidity(20);
        weather.setPressure(10);
        weather.setTemperature(-25);

        return weather;
    }

    private Weather getTestWeather2(){
        Weather weather = new Weather();

        Location location = new Location();
        location.setCityName("Test City2");
        location.setRegion("Region Test2");
        location.setCountry("Test Country2");
        location.setLongitude(12);
        location.setLatitude(30);

        weather.setLocation(testEntityManager.persist(location));
        weather.setDate("02.20.2020");
        weather.setWindSpeed(20);
        weather.setWindDirection("N");
        weather.setHumidity(10);
        weather.setPressure(5);
        weather.setTemperature(30);

        return weather;
    }

    @Test
    void when_there_are_no_items_in_db_then_repo_should_return_empty_list(){
        //given

        //when

        List<Weather>  weatherList = weatherRepository.findAll();

        //then

        assertEquals(0,weatherList.size());
    }

    @Test
    void add_weather_then_does_not_thrown_there_is_no_such_element(){
        //given
        Weather weather = getTestWeather();

        //when
        testEntityManager.persist(weather);

        //then

        assertDoesNotThrow(() ->weatherRepository.findAll().stream().findAny().orElseThrow(NoSuchElementException::new));

    }

    @Test
    void add_weather_and_delete_it_then_return_empty_list(){
        //given
        Weather weather = getTestWeather();


        //when
        testEntityManager.persist(weather);
        testEntityManager.remove(weather);
        List<Weather> weatherList = weatherRepository.findAll();
        //then
        assertEquals(0,weatherList.size());

    }

    @Test
    void add_weather_and_update_it_then_return_updated_element(){
        //given
        Weather weather = getTestWeather();

        //when
        testEntityManager.persist(weather);
        weather.setTemperature(15);
        testEntityManager.persist(weather);
        List<Weather> weatherList = weatherRepository.findAll();

        //then
        assertEquals(weather,weatherList.get(0));

    }

    @Test
    void add_weathers_then_find_elements_by_date_and_location_then_return_that_elements(){
        // given
        Weather weather1 = getTestWeather();
        Location location1 = weather1.getLocation();
        String date1 = weather1.getDate();

        Weather weather2 = getTestWeather2();
        Location location2 = weather2.getLocation();
        String date2 = weather2.getDate();

        // when
        testEntityManager.persist(weather1);
        testEntityManager.persist(weather2);

        Weather searchedWeather1 = weatherRepository.findByDateAndLocation(date1,location1);
        Weather searchedWeather2 = weatherRepository.findByDateAndLocation(date2,location2);

        // then
        assertEquals(weather1,searchedWeather1);
        assertEquals(weather2,searchedWeather2);
    }

    @Test
    void add_weather_when_looking_for_element_by_date_and_different_location_then_return_none_weather(){
        //given
        Weather weather = getTestWeather();
        String date = weather.getDate();

        Location location = new Location();
        location.setLongitude(10);
        location.setLongitude(2);
        location.setCountry("Country");
        location.setRegion("Region");
        location.setCityName("City");
        location = testEntityManager.persist(location);
                //locationRepository.save(location);

        //when
        testEntityManager.persist(weather);
        Weather searchedWeather = weatherRepository.findByDateAndLocation(date,location);
        //then

        assertNull(searchedWeather);

    }

    @Test
    void add_weathers_then_find_elements_by_location_then_return_that_elements() {
        // given
        Weather weather1 = getTestWeather();
        Location location1 = weather1.getLocation();

        Weather weather2 = getTestWeather2();
        Location location2 = weather2.getLocation();

        // when
        weather1 = testEntityManager.persist(weather1);
        weather2 = testEntityManager.persist(weather2);

        Weather searchedWeather1 = weatherRepository.findByLocation(location1).stream().findAny().orElse(new Weather());
        Weather searchedWeather2 = weatherRepository.findByLocation(location2).stream().findAny().orElse(new Weather());

        // then

        assertEquals(weather1,searchedWeather1);
        assertEquals(weather2,searchedWeather2);



    }

    @Test
    void add_weather_when_find_element_by_different_location_then_return_none_weather(){
        //given
        Weather weather = getTestWeather();

        Location location = new Location();
        location.setLongitude(10);
        location.setLongitude(2);
        location.setCountry("Country");
        location.setRegion("Region");
        location.setCityName("City");
        location = testEntityManager.persist(location);
                //locationRepository.save(location);
        //when
        testEntityManager.persist(weather);
        Location finalLocation = location;
        //then

        assertThrows(NoSuchElementException.class,() ->
            weatherRepository.findByLocation(finalLocation).stream().findAny().orElseThrow(NoSuchElementException::new)
         );

    }

    @Test
    void add_weathers_then_find_by_different_date_and_get_empty_list(){
        //given
        String date = "20.02.1999";
        testEntityManager.persist(getTestWeather());
        testEntityManager.persist(getTestWeather2());

        //then
        List<Weather> result = weatherRepository.findByDate(date);

        //when
        assertEquals(0,result.size());


    }


}