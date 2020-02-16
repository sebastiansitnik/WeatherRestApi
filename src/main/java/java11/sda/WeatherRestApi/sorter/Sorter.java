package java11.sda.WeatherRestApi.sorter;

import org.springframework.data.domain.Sort;

public class Sorter {

    public Sort setupSort(String properties, boolean ascending) {

        Sort.Direction sortDirection;

        sortDirection = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;

        return Sort.by(sortDirection, properties);
    }
}
