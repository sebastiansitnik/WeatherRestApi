package java11.sda.WeatherRestApi.Location;

public enum LocationSortProperties {

    PLACE_NAME("cityName");

    String property;

    LocationSortProperties(String property) {
        this.property = property;
    }
}
