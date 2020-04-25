package java11.sda.WeatherRestApi;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<Object> thereIsNoSuchElementInDataBase(NoSuchElementException e) {
        return new ResponseEntity<>("There is no such element in data base", HttpStatus.NOT_FOUND);
    }


}
