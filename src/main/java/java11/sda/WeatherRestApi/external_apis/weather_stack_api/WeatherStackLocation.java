package java11.sda.WeatherRestApi.external_apis.weather_stack_api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherStackLocation {

    private String name;
    private String country;
    private String region;
    private String lat;
    private String lon;
    @JsonProperty(value = "timezone_id")
    private String timezoneId;
    private String localtime;
    @JsonProperty(value = "localtime_epoch")
    private String localtimeEpoch;
    @JsonProperty(value = "utc_offset")
    private String utcOffset;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(String timezoneId) {
        this.timezoneId = timezoneId;
    }

    public String getLocaltime() {
        return localtime;
    }

    public void setLocaltime(String localtime) {
        this.localtime = localtime;
    }

    public String getLocaltimeEpoch() {
        return localtimeEpoch;
    }

    public void setLocaltimeEpoch(String localtimeEpoch) {
        this.localtimeEpoch = localtimeEpoch;
    }

    public String getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(String utcOffset) {
        this.utcOffset = utcOffset;
    }
}
