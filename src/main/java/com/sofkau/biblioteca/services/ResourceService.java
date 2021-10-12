package com.sofkau.biblioteca.services;

import com.sofkau.biblioteca.dtos.ResourceDTO;
import com.sofkau.biblioteca.mappers.ResourceMapper;
import com.sofkau.biblioteca.models.Resource;
import com.sofkau.biblioteca.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ResourceService {
    private ResourceRepository repository;
    private ResourceMapper mapper;

    @Autowired
    public ResourceService(ResourceRepository repository, ResourceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ResourceDTO> getAll() {
        List<ResourceDTO> resourceDTOS = new ArrayList<>();
        repository.findAll().forEach(resource -> resourceDTOS.add(mapper.convertToDto(resource)));
        return resourceDTOS;
    }

    public ResourceDTO save(ResourceDTO resourceDTO) {
        if (resourceDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("el nombre no puede estar vacio");
        }
        Resource resource = mapper.convertToDocument(resourceDTO);
        return mapper.convertToDto(repository.insert(resource));
    }

    public ResourceDTO getById(String id) {
        Optional<Resource> resource = repository.findById(id);
        if (resource.isEmpty()) {
            throw new NoSuchElementException("no existe un recurso con el id: " + id);
        }
        return mapper.convertToDto(resource.get());
    }

    public void delete(String id) {
        repository.delete(mapper.convertToDocument(getById(id)));
    }

    public ResourceDTO update(ResourceDTO resourceDTO) {
        Resource resource = mapper.convertToDocument(resourceDTO);
        getById(resource.getId());
        return mapper.convertToDto(repository.save(resource));
    }

    public String checkAvailability(String id) {
        ResourceDTO dto = getById(id);
        if (isAvailable(dto)) {
            return "El recurso " + dto.getName() + " esta disponible";
        }
        return "el recurso " + dto.getName() + " no esta disponible " + " fue prestado " + dto.getLoanDate();
    }

    private boolean isAvailable(ResourceDTO dto) {
        return dto.getQuantityAvailable() > dto.getQuantityBorrowed();
    }

    public String lend(String id) {
        ResourceDTO dto = getById(id);
        if (isAvailable(dto)) {
            dto.setQuantityBorrowed(dto.getQuantityBorrowed() + 1);
            dto.setLoanDate(LocalDate.now());
            update(dto);
            return "El recurso " + dto.getName() + " se ha prestado";
        }
        return "el recurso " + dto.getName() + " no esta disponible para prestarse";
    }

    public List<ResourceDTO> recommendByType(String type) {
        List<ResourceDTO> resourceDTOS = new ArrayList<>();
        repository.findByType(type).forEach(resource -> resourceDTOS.add(mapper.convertToDto(resource)));
        return resourceDTOS;
    }

    public List<ResourceDTO> recommendByTheme(String theme) {
        List<ResourceDTO> resourceDTOS = new ArrayList<>();
        repository.findByThematic(theme).forEach(resource -> resourceDTOS.add(mapper.convertToDto(resource)));
        return resourceDTOS;
    }

    public List<ResourceDTO> recommendByTypeAndTheme(String type, String theme) {
        List<ResourceDTO> resourceDTOS = new ArrayList<>();
        List<ResourceDTO> resourceAux = new ArrayList<>();
        resourceAux.addAll(recommendByTheme(theme));
        resourceAux.addAll(recommendByType(type));
        resourceAux.stream().distinct().forEach(resourceDTO -> resourceDTOS.add(resourceDTO));
        return resourceDTOS;
    }

    public String returnResource(String id) {
        ResourceDTO dto = getById(id);
        if (dto.getQuantityBorrowed() > 0) {
            dto.setQuantityBorrowed(dto.getQuantityBorrowed() - 1);
            update(dto);
            return "El recurso " + dto.getName() + " se ha devuelto";
        }
        return "el recurso " + dto.getName() + " no se puede devolver, porque no se ha prestado";
    }
}
