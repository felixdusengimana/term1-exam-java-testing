package rw.ac.rca.termOneExam.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {
    @Mock
    private ICityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @Test
//    get_all success service tests
    public void getAll_withSomeElements() {

        when(cityRepository.findAll()).thenReturn(Arrays.asList(new City(1,"Kanombe",10,0),
                new City(2,"Kicukiro",40,0)));
        assertEquals(10,cityService.getAll().get(0).getWeather());
        assertEquals(2, cityService.getAll().size());
    }


    @Test
    public  void getById(){
        City city = new City(1L,"Kigali",54,5);
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        assertEquals(1,cityService.getById(1).get().getId());
        assertEquals("Kigali",cityService.getById(1).get().getName());

    }


    @Test
//    get_all fail service tests
    public void getById_Fail() {
        when(cityRepository.findById(100L)).thenReturn(Optional.empty());

        assertEquals(false, cityService.getById(100L).isPresent());
    }

    @Test
    public void add_success() {
        CreateCityDTO cityDTO = new CreateCityDTO();
        cityDTO.setName("Rubavu");
        cityDTO.setWeather(33);
        when(cityRepository.save(any(City.class))).thenReturn(new City(cityDTO.getName(),cityDTO.getWeather()));

        assertEquals(cityDTO.getName(), cityService.save(cityDTO).getName());
    }

    @Test
    public void saveCity_fail(){

        City city = new City ();
        CreateCityDTO employeeDTO = new CreateCityDTO ();


        when(cityRepository.save(city)).thenReturn(city);

        assertEquals(null, cityService.save(employeeDTO));
    }

    @Test
    public void existByName_success() {
        when(cityRepository.existsByName("Kigali")).thenReturn(true);

        assertEquals(true, cityService.existsByName("Kigali"));
    }

    @Test
    public void existByName_fail() {
        when(cityRepository.existsByName("Kigaliii")).thenReturn(false);

        assertEquals(false, cityService.existsByName("Kigaliii"));
    }

}
