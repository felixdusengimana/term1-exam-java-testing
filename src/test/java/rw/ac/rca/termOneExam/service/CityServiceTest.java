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
        Long id = 1000L;
        when(cityRepository.findById(id)).thenReturn(Optional.empty());
        Optional<City> city = cityService.getById(id);
        assertTrue(city.get() == null);
    }

    @Test
    public void add_success() {
        CreateCityDTO dto = new CreateCityDTO();
        dto.setName("Kigali");
        dto.setWeather(24);

        City city = new City(dto.getName(), dto.getWeather());

        when(cityRepository.save(city)).thenReturn(city);

        assertTrue(cityService.save(dto).getName() == "Kigali");
    }

    @Test
    public void saveCity_fail(){

        City city = new City ();
        CreateCityDTO employeeDTO = new CreateCityDTO ();


        when(cityRepository.save(city)).thenReturn(city);

        assertEquals(null, cityService.save(employeeDTO));
    }
}
