package java11.sda.WeatherRestApi.location;

import java11.sda.WeatherRestApi.weather.WeatherDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class LocationDTO {

    private String id;
    @NotEmpty
    private float longitude;
    @NotEmpty
    private float latitude;
    @NotEmpty
    private String cityName;
    @NotNull
    private String region;
    @NotEmpty
    private String country;
    private List<WeatherDTO> weathers = new ArrayList<>();

    public List<WeatherDTO> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<WeatherDTO> weathers) {
        this.weathers = weathers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
