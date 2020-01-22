package java11.sda.WeatherRestApi.Weather;

import java11.sda.WeatherRestApi.Location.Location;
import java11.sda.WeatherRestApi.Location.LocationDTOTransformer;
import java11.sda.WeatherRestApi.Location.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.configuration.injection.MockInjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WeatherServiceTest {

//    @TestConfiguration
//    static class WeatherServiceTestConfiguration{
//
//        @Bean
//        public WeatherService weatherService(WeatherRepository weatherRepository,
//                                             LocationRepository locationRepository,
//                                             WeatherDTOTransformer weatherDTOTransformer,
//                                             LocationDTOTransformer locationDTOTransformer) {
//            return new WeatherService(weatherRepository,
//                    locationRepository,weatherDTOTransformer,locationDTOTransformer);
//        }
//
//    }

    @MockBean
    private WeatherRepository weatherRepository;
    @MockBean
    private LocationRepository locationRepository;



    @Autowired
    private WeatherService weatherService;


    @Test
    void when_add_valid_then_get_it(){

        Location location = new Location();
        location.setId("1");
        //given
        Weather weather = new Weather(0,0,0,"s",0,"20.12.2020",location);

        WeatherDTO weatherDTO = new WeatherDTO(0,0,0,"s",0,"20.12.2020"," "," ");
        weatherDTO.setLocationId("1");
        weatherDTO.setId("1");
        //when
        Mockito.when(locationRepository.findById(weather.getLocation().getId())).thenReturn(Optional.of(location));

        Mockito.when(weatherRepository.findByDateAndLocation(weather.getDate(),location)).thenReturn(null);
        weatherService.create(weatherDTO);

        //then

        Mockito.verify(weatherRepository).save(weather);
    }

}