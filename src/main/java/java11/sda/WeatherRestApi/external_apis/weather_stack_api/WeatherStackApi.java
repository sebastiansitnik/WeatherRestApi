package java11.sda.WeatherRestApi.external_apis.weather_stack_api;


public class WeatherStackApi {

    public static final String ACCESS_FOR_CITY = "&query=";

    public static final String ACCESS_KEY = "/current?access_key=d543410ffab3a814bf925aaca5f9e545";

    public static final String URL = "http://api.weatherstack.com";


    private WeatherStackResponse weatherStackResponse;

    public WeatherStackResponse getWeatherStackResponse() {
        return weatherStackResponse;
    }

    public void setWeatherStackResponse(WeatherStackResponse weatherStackResponse) {
        this.weatherStackResponse = weatherStackResponse;
    }
}
