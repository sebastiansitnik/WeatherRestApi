package java11.sda.WeatherRestApi.Weather;

import java11.sda.WeatherRestApi.Location.*;
import java11.sda.WeatherRestApi.Location.external_api.weather_stack.LocationJsonService;
import java11.sda.WeatherRestApi.Weather.external_api.weather_stack.WeatherJsonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class WeatherServiceTest {

////    @TestConfiguration
////    static class WeatherServiceTestConfiguration{
////
////        @Bean
////        public WeatherService weatherService(WeatherRepository weatherRepository,
////                                             LocationRepository locationRepository,
////                                             WeatherDTOTransformer weatherDTOTransformer,
////                                             LocationDTOTransformer locationDTOTransformer,
////                                             WeatherJsonService weatherJsonService,
////                                             LocationService locationService) {
////            return new WeatherService(weatherRepository, locationRepository,
////                    weatherDTOTransformer, locationDTOTransformer,
////                    weatherJsonService, locationService);
////        }
////
//////    }
////    @MockBean
////    WeatherJsonService weatherJsonService;
////
////    @MockBean
////    LocationJsonService locationJsonService;
//
//    @Autowired
//    private WeatherService weatherService;
//
//    @MockBean
//    private WeatherRepository weatherRepository;
//    @MockBean
//    private LocationRepository locationRepository;
//
//    @MockBean
//    private WeatherDTOTransformer weatherDTOTransformer;
//
//    @MockBean
//    private LocationDTOTransformer locationDTOTransformer;
//
//    @Test
//    void when_add_valid_then_get_it(){
//
//        Location location = new Location();
//        location.setId("1");
//        //given
//        Weather weather = new Weather();
//
//        weather.setTemperature(0);
//        weather.setPressure(0);
//        weather.setHumidity(0);
//        weather.setWindDirection("S");
//        weather.setWindSpeed(0);
//        weather.setDate(" ");
//        weather.setLocation(location);
//
//        WeatherDTO weatherDTO = new WeatherDTO(0,0,0,"s",0,"20.12.2020"," "," ");
//        weatherDTO.setLocationId("1");
//        weatherDTO.setId("1");
//        //when
//        Mockito.when(locationRepository.findById(weather.getLocation().getId())).thenReturn(Optional.of(location));
//        Mockito.when(weatherRepository.findByDateAndLocation(weather.getDate(),location)).thenReturn(null);
//        Mockito.when(weatherDTOTransformer.toEntity(weatherDTO)).thenReturn(weather);
//        weatherService.create(weatherDTO);
//
//        //then
//
//        Mockito.verify(weatherRepository).save(weather);
//    }

}