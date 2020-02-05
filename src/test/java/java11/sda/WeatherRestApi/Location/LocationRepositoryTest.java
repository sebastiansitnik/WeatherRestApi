package java11.sda.WeatherRestApi.Location;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Location testLocation(){
        Location location = new Location();
        location.setCityName("Kundera");
        location.setRegion("Liptfu");
        location.setCountry("Kaldi");
        location.setLongitude(1212);
        location.setLatitude(123);

        return location;
    }


    @Test
    void add_location_then_check_if_its_the_same(){
        //given
        Location location = testLocation();

        //when
        testEntityManager.persist(location);

        //then
        assertEquals(location,locationRepository.findAll().stream().findFirst().orElse(new Location()));
    }

    @Test
    void add_location_then_delete_it_and_check_if_repository_is_empty(){
        //given
        Location location = testLocation();

        //when
        testEntityManager.persist(location);
        testEntityManager.remove(location);

        //then
        assertEquals(0,locationRepository.findAll().size());
    }

    @Test
    void add_location_and_update_it_then_check_if_its_updated(){
        //given
        Location location = testLocation();

        //when
        testEntityManager.persist(location);
        location.setLatitude(540);
        testEntityManager.persist(location);

        //then
        locationRepository.findAll().stream().findFirst().orElseThrow(NoSuchElementException::new);
    }


    @Test
    void findByCityName() {
    }

    @Test
    void findByRegion() {
    }

    @Test
    void findByCountry() {
    }

    @Test
    void findByLatitudeAndLongitudeAndCityName() {
    }

    @Test
    void findByLatitudeAndLongitude() {
    }
}