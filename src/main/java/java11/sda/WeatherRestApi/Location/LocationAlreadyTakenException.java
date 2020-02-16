package java11.sda.WeatherRestApi.location;

public class LocationAlreadyTakenException extends RuntimeException {

    public LocationAlreadyTakenException() {
        super("Location already exist");
    }
}
