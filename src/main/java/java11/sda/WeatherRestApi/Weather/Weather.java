package java11.sda.WeatherRestApi.Weather;

import java11.sda.WeatherRestApi.Location.Location;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Weather {

    @Id
    private long id;
    private float temperature;
    private float pressure;
    private float humidity;
    private String Wind_direction;
    private float Wind_speed;
    private String date;

    @ManyToOne
    private Location location;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public String getWind_direction() {
        return Wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        Wind_direction = wind_direction;
    }

    public float getWind_speed() {
        return Wind_speed;
    }

    public void setWind_speed(float wind_speed) {
        Wind_speed = wind_speed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
