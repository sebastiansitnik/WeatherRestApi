package java11.sda.WeatherRestApi.Location;

public class LocationAlreadyTakenException extends RuntimeException {

    public LocationAlreadyTakenException() {
        super("Location already exist");
    }
}
