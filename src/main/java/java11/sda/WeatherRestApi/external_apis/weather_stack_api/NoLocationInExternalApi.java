package java11.sda.WeatherRestApi.external_apis.weather_stack_api;

public class NoLocationInExternalApi extends RuntimeException {

    public NoLocationInExternalApi() {super("Such location dont exist or we dont have it our base");}
}
