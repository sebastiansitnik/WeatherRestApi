package java11.sda.WeatherRestApi.Location;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LocationController.class)
class LocationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LocationService locationService;

    @Test
    void when_request_get_all_then_locations_should_be_removed() throws Exception {
        //given
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLatitude(0.0f);

        Mockito.when(locationService.readAll()).thenReturn(Collections.singletonList(
                locationDTO));

        //when and then
        mvc.perform(MockMvcRequestBuilders.get(("/location")).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$" , hasSize(1)))
                .andExpect(jsonPath("$[0].latitude",equalTo(0.0)));

    }

    @Test
    void when_adding_return_proper_location() throws Exception {
        //given

        //when nad then
        mvc.perform(MockMvcRequestBuilders.post(("/location")).contentType(MediaType.APPLICATION_JSON)
                .content("{\"cityName\":\"Szczecin\", \"region\":\"ZachPom\"}"))
                .andExpect(status().isOk());

    }

    @Test
    void when_delete_return_deleted_location() throws Exception {
        //given
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setCityName("Szczecin");
        locationDTO.setRegion("ZachPom");
        locationDTO.setId("asdasda");
        Mockito.when(locationService.delete("asdasda")).thenReturn(locationDTO);

        //when and then
        mvc.perform(MockMvcRequestBuilders.delete(("/location")).contentType(MediaType.APPLICATION_JSON)
                .content("{\"cityName\":\"Szczecin\", \"region\":\"ZachPom\", \" id\" : \"asdasda\"}"))
                .andExpect(jsonPath("$", equalTo(locationDTO)));
    }

}