package com.vitoria.demo_api.web.controllers.mapper;
import com.vitoria.demo_api.domain.User;
import com.vitoria.demo_api.web.controllers.dtos.UserCreateDTO;
import com.vitoria.demo_api.web.controllers.dtos.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toUser(UserCreateDTO userCreateDTO){
        return new ModelMapper().map(userCreateDTO, User.class);
    }

    public static UserResponseDTO toDTO(User user){
        String role = user.getRole().name().substring("ROLE_".length());
        PropertyMap<User, UserResponseDTO> props = new PropertyMap<User, UserResponseDTO>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(user, UserResponseDTO.class);
    }

    public static List<UserResponseDTO> toListDto(List<User> user){
       return user.stream().map(savedUser -> toDTO(savedUser)).collect(Collectors.toList());
    }




}
