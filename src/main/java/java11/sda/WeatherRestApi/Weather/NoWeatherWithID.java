package java11.sda.WeatherRestApi.weather;

public class NoWeatherWithID extends RuntimeException {

    public NoWeatherWithID(){super("No weather with that ID in data base");}
}
