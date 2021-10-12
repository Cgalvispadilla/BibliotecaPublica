package com.sofkau.biblioteca.controllers;

import com.sofkau.biblioteca.dtos.ResourceDTO;
import com.sofkau.biblioteca.services.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recursos")
public class ResourceController {
    Logger logger = LoggerFactory.getLogger(ResourceController.class);
    private ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping("/agregar")
    public ResponseEntity<ResourceDTO> add(@RequestBody ResourceDTO resourceDTO){
        return new ResponseEntity(resourceService.save(resourceDTO), HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<ResourceDTO> findAll(){
        return new ResponseEntity(resourceService.getAll(),HttpStatus.OK);
    }

    @PutMapping("/editar")
    public ResponseEntity<ResourceDTO> edit(@RequestBody ResourceDTO resourceDTO){
        if(!resourceDTO.getId().isEmpty()){
            return new ResponseEntity(resourceService.update(resourceDTO),HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id){
        try {
            resourceService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            logger.error("ocurrio el siguiente error: "+e);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/disponibilidad/{id}")
    public ResponseEntity availability(@PathVariable("id") String id){
        return new ResponseEntity(resourceService.checkAvailability(id), HttpStatus.OK);
    }

}
