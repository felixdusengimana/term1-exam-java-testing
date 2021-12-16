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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.utils.APICustomResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class CityControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
//    Getting all success test
    public void getAll_success() throws JSONException {
        String response = this.restTemplate.getForObject("/api/cities/all", String.class);
        JSONAssert.assertEquals("[{\"id\":101,\"name\":\"Kigali\",\"weather\":24.0,\"fahrenheit\":75.2},{\"id\":102,\"name\":\"Musanze\",\"weather\":18.0,\"fahrenheit\":64.4},{\"id\":103,\"name\":\"Rubavu\",\"weather\":20.0,\"fahrenheit\":68.0},{\"id\":104,\"name\":\"Nyagatare\",\"weather\":28.0,\"fahrenheit\":82.4}]\n", response, true);
    }

//
//    @Test
////    Getting all fail test
//    public void getAll_fail() throws JSONException {
//        String response = this.restTemplate.getForObject("/api/cities/all", String.class);
//        JSONAssert.assertEquals(null,response, false);
//    }
//

    @Test
//    Get one by id success
    public void getById_successEntity() throws JSONException {
        ResponseEntity<City> response = this.restTemplate.getForEntity("/api/cities/id/104", City.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(104, response.getBody().getId());
        assertEquals("Nyagatare", response.getBody().getName());

    }

    @Test
//    Get by Id fail
    public void getById_404() {
        ResponseEntity<APICustomResponse> response = this.restTemplate.getForEntity("/api/cities/id/1000", APICustomResponse.class);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("City not found with id 1000", response.getBody().getMessage());
    }


    @Test
//    create city success
    public void createCity_Success() {

        CreateCityDTO cityDTO=new CreateCityDTO();
        cityDTO.setName("Byumba");
        cityDTO.setWeather(32.2);

        ResponseEntity<City> response = this.restTemplate.postForEntity("/api/cities/add", cityDTO, City.class);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Byumba",response.getBody().getName());
    }

    @Test
//    create city fail
    public void createCity_Fail() {

        CreateCityDTO cityDTO=new CreateCityDTO();
        cityDTO.setName("Kigali");

        ResponseEntity<APICustomResponse> response = this.restTemplate.postForEntity("/api/cities/add", cityDTO, APICustomResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("City name Kigali is registered already",response.getBody().getMessage());
    }
}
