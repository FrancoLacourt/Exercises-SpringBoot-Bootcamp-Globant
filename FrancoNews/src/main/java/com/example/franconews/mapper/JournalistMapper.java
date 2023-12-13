package com.example.franconews.mapper;

import com.example.franconews.dto.JournalistDTO;
import com.example.franconews.entities.Journalist;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface JournalistMapper {

    JournalistDTO journalistToJournalistDTO(Journalist journalist);
    Journalist journalistDTOToJournalist(JournalistDTO journalistDTO);
}
