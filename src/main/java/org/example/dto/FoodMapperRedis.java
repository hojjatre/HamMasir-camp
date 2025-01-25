package org.example.dto;

import org.example.model.Food;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Mapper
public interface FoodMapperRedis {
    FoodMapperRedis instanse = Mappers.getMapper(FoodMapperRedis.class);

    @Mapping(source = "foodID", target = "foodID")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "typeFood", target = "typeFood")
    @Mapping(source = "cost", target = "cost")
    @Mapping(source = "description", target = "description")
    FoodDTOredis entityToDTO(Food food);

    @Mapping(source = "foodID", target = "foodID")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "typeFood", target = "typeFood")
    @Mapping(source = "cost", target = "cost")
    @Mapping(source = "description", target = "description")
    Food dtoTOEntity(FoodDTOredis foodDTOredis);
}
