package rw.ac.rca.termOneExam.service;

import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CityServiceTest {

    @Mock
    private ICityRepository cityRepositoryMock;

    @InjectMocks
    private CityService  cityService;

    @Test
    public void save_newCity() {
        CreateCityDTO dto = new CreateCityDTO();
        dto.setName("Kigali");
        dto.setWeather(24);
        City city = new City(dto.getName(), dto.getWeather());
        when(cityRepositoryMock.save(city)).thenReturn(city);
        City createCity = cityService.save(dto);
        assertTrue(createCity.getName() == "Kigali");
    }

    @Test
    public void getAll_success() {

        when(cityRepositoryMock.findAll()).thenReturn(Arrays.asList(new City(104, "Kigali", 24, 70),
                new City(2, "Nyabihu", 26, 10)));
        assertEquals(75.2, cityService.getAll().get(0).getFahrenheit());
    }
    @Test
    public void getAllCities(){
        List<City> cities = new ArrayList();
        cities.add(new City());
        given(cityRepositoryMock.findAll()).willReturn(cities);
        assertEquals(cityService.getAll(), cities);
        verify(cityRepositoryMock).findAll();
    }

    @Test
    public void getCity_ById(){
        Long id = 104L;
        City city = new City(id,"Kigali",24,5);
        when(cityRepositoryMock.findById(id)).thenReturn(Optional.of(city));
        assertEquals(75.2,cityService.getById(id).getFahrenheit());
    }
}
