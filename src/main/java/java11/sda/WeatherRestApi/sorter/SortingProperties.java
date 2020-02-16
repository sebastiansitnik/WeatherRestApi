package java11.sda.WeatherRestApi.sorter;

public enum  SortingProperties {

    SORTING_BY_CITY_NAME("cityName"),
    SORTING_BY_DATE("date");

    public String property;

    SortingProperties(String property) {
        this.property = property;
    }
}
