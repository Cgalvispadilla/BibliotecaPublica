package com.sofkau.biblioteca.services;

import com.sofkau.biblioteca.dtos.ResourceDTO;
import com.sofkau.biblioteca.mappers.ResourceMapper;
import com.sofkau.biblioteca.models.Resource;
import com.sofkau.biblioteca.repositories.ResourceRepository;
import org.apache.catalina.mapper.Mapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceServiceTest {
    @MockBean
    private ResourceRepository repository;
    @Autowired
    private ResourceService resourceService;


    @Test
    @DisplayName("Test para obtener todos los recursos de manera exitosa")
    void getAll() {
        var recursoOne = new Resource();

        Mockito.when(repository.findAll()).thenReturn(recursos());
        var resultado = resourceService.getAll();
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("R-111", resultado.get(0).getId());
        Assertions.assertEquals("Revista xyz", resultado.get(0).getName());
        Assertions.assertEquals(2, resultado.get(0).getQuantityAvailable());
        Assertions.assertEquals(null, resultado.get(0).getLoanDate());
        Assertions.assertEquals(0, resultado.get(0).getQuantityBorrowed());
        Assertions.assertEquals("Revista", resultado.get(0).getType());
        Assertions.assertEquals("Farandula", resultado.get(0).getThematic());
        Assertions.assertEquals("L-111", resultado.get(1).getId());
        Assertions.assertEquals("Libro xyz", resultado.get(1).getName());
        Assertions.assertEquals(2, resultado.get(1).getQuantityAvailable());
        Assertions.assertEquals(LocalDate.now(), resultado.get(1).getLoanDate());
        Assertions.assertEquals(1, resultado.get(1).getQuantityBorrowed());
        Assertions.assertEquals("Libro", resultado.get(1).getType());
        Assertions.assertEquals("Terror", resultado.get(1).getThematic());
    }

    private List<Resource> recursos() {

        var recursoOne = new Resource();
        recursoOne.setId("R-111");
        recursoOne.setName("Revista xyz");
        recursoOne.setQuantityAvailable(2);
        recursoOne.setLoanDate(null);
        recursoOne.setQuantityBorrowed(0);
        recursoOne.setType("Revista");
        recursoOne.setThematic("Farandula");
        var recursoTwo = new Resource();
        recursoTwo.setId("L-111");
        recursoTwo.setName("Libro xyz");
        recursoTwo.setQuantityAvailable(2);
        recursoTwo.setLoanDate(LocalDate.now());
        recursoTwo.setQuantityBorrowed(1);
        recursoTwo.setType("Libro");
        recursoTwo.setThematic("Terror");//
        var recursos = new ArrayList<Resource>();
        recursos.add(recursoOne);
        recursos.add(recursoTwo);
        return recursos;
    }

    @Test
    @DisplayName("Test para crear recurso de manera exitosa")
    void save() {
        var recursoOne = new ResourceDTO();
        recursoOne.setName("Revista xyz");
        recursoOne.setQuantityAvailable(2);
        recursoOne.setLoanDate(null);
        recursoOne.setQuantityBorrowed(0);
        recursoOne.setType("Revista");
        recursoOne.setThematic("Farandula");

        Mockito.when(repository.save(Mockito.any())).thenReturn(recursos().get(0));

        var resultado = resourceService.save(recursoOne);

        Assertions.assertNotNull(resultado, "el dato guardado no debe ser nullo");

        Assertions.assertEquals("Revista xyz", resultado.getName(),"el nombre debe corresponder");
        Assertions.assertEquals(2, resultado.getQuantityAvailable(),"la cantidad disponible debe ser igual");
        Assertions.assertEquals(null, resultado.getLoanDate(),"la fecha de cuando se presto debe estar nula");
        Assertions.assertEquals(0, resultado.getQuantityBorrowed(),"la cantidad prestada debe ser cero");
        Assertions.assertEquals("Revista", resultado.getType(),"el tipo debe coincidir ");
        Assertions.assertEquals("Farandula", resultado.getThematic(),"la tematica debe coincidir");

    }

    @Test
    void getById() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void checkAvailability() {
    }

    @Test
    void lend() {
    }

    @Test
    void recommendByType() {
    }

    @Test
    void recommendByTheme() {
    }

    @Test
    void recommendByTypeAndTheme() {
    }

    @Test
    void returnResource() {
    }
}