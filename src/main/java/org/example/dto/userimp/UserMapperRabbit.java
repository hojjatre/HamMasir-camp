package org.example.dto.userimp;

import org.example.model.UserImp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapperRabbit {
    UserMapperRabbit instance = Mappers.getMapper(UserMapperRabbit.class);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    UserRabbitMQ entityToRabbit(UserImp userImp);
}
