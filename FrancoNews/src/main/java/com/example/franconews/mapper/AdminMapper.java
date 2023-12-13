package com.example.franconews.mapper;

import com.example.franconews.dto.AdminDTO;
import com.example.franconews.entities.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdminMapper {

    AdminDTO adminToAdminDTO(Admin admin);
    Admin adminDTOToAdmin(AdminDTO adminDTO);
}
