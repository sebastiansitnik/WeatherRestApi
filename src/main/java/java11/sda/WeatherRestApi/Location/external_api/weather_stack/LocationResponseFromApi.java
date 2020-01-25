package java11.sda.WeatherRestApi.Location.external_api.weather_stack;

import java11.sda.WeatherRestApi.Location.Location;


public class LocationResponseFromApi {

    private Location location;

    public LocationResponseFromApi() {
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
