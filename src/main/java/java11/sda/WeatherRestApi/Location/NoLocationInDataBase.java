package java11.sda.WeatherRestApi.location;

public class NoLocationInDataBase extends RuntimeException {

    public NoLocationInDataBase() {
        super("No location in data base");
    }
}
