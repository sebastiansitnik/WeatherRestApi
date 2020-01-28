package java11.sda.WeatherRestApi.Weather;

public enum WeatherSortingProperties {

    SORTING_BY_DATE("date");

    String property;

    WeatherSortingProperties(String property) {
        this.property = property;
    }
}
