package com.sofkau.biblioteca.mappers;

import com.sofkau.biblioteca.dtos.ResourceDTO;
import com.sofkau.biblioteca.models.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapper {
    private ModelMapper mapper;

    public ResourceMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }
    public ResourceDTO convertToDto(Resource resource){
        ResourceDTO resourceDTO = mapper.map(resource, ResourceDTO.class);
        return resourceDTO;
    }
    public Resource convertToDocument(ResourceDTO resourceDTO){
        Resource resource= mapper.map(resourceDTO,Resource.class);
        return resource;
    }
}
