package java11.sda.WeatherRestApi.Weather;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class WeatherRepositoryTest {

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void when_there_are_no_items_in_db_then_repo_should_return_empty_list(){
        //given

        //when

        List<Weather>  weatherList = weatherRepository.findAll();

        //then

        assertEquals(0,weatherList.size());
    }
}