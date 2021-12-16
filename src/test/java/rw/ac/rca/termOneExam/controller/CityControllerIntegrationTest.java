package rw.ac.rca.termOneExam.controller;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.utils.APICustomResponse;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private String apiPath = "/api/cities";

    @Test
    public void getAll_Success() throws JSONException {
        String response = this.restTemplate.getForObject(apiPath+"/all-cities", String.class);
        JSONAssert.assertEquals("[{id:101},{id:102},{id:103},{id:104}]", response, false);
    }

    @Test
    public void getById_success() throws JSONException {
        ResponseEntity<City> city = this.restTemplate.getForEntity(apiPath+"/id/101",City.class);

        assertTrue(city.getStatusCode().is2xxSuccessful());
        assertEquals("Kigali",city.getBody().getName());
        assertEquals(24,city.getBody().getWeather());

    }

    @Test
    public void getById_404() throws JSONException {
        ResponseEntity<APICustomResponse> response = this.restTemplate.getForEntity(apiPath+"/id/9900",APICustomResponse.class);

        assertTrue(response.getStatusCodeValue()==404);
        assertFalse(response.getBody().isStatus());
        assertEquals("No city found with id 9900",response.getBody().getMessage());

    }

    @Test
    public void newCity_Success() {
        City city = new City("Kirehe",23);
        ResponseEntity<City> response = restTemplate.postForEntity(apiPath+"/add", city, City.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Kirehe", Objects.requireNonNull(response.getBody()).getName());
        assertEquals(0.0, Objects.requireNonNull(response.getBody()).getFahrenheit(),8.9);

    }


    @Test
    public void New_BadRequest() {
        City theCity = new City("Musanze",26);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/cities/add", theCity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void findById_Success() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/cities/id/104", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findById_NotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/cities/id/1", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}