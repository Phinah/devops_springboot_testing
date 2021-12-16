package rw.ac.rca.termOneExam.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
public class CityUtilTest {

    @Autowired
    private ICityRepository cityRepository;

    @Test
    public void city_rubavu_kigali() {
        boolean found = false;
        List<City> cities = cityRepository.findAll();
        assertEquals(true, cityRepository.existsByName("Rubavu") && cityRepository.existsByName("Kigali"));
    }

    @Test
    public void cities_with_Weather_less_than_30() {
        boolean result = false;
        List<City> cities = cityRepository.findAll();
        for (City city : cities)
            if (city.getWeather() > 30)
                result = true;


        assertEquals(false, result);
    }

    @Test
    public void cities_with_Weather_greater_than_20() {
        boolean result = false;
        List<City> cities = cityRepository.findAll();
        for (City city : cities)
            if (city.getWeather() < 20)
                result = false;


        assertEquals(false, result);
    }
}
