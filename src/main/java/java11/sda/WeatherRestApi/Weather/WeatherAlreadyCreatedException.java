package java11.sda.WeatherRestApi.weather;

public class WeatherAlreadyCreatedException extends RuntimeException {

    public WeatherAlreadyCreatedException() {
            super("Weather already created!");
        }

}
