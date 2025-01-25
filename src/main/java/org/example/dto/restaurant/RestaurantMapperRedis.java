package org.example.dto.restaurant;

import org.example.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestaurantMapperRedis {
    RestaurantMapperRedis instance =  Mappers.getMapper(RestaurantMapperRedis.class);

    @Mapping(source = "restaurantID", target = "restaurantID")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "location", target = "location")
    RestaurantDTOredis entityToDTO(RestaurantView restaurantView);


    @Mapping(source = "restaurantID", target = "restaurantID")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "location", target = "location")
    RestaurantDTOredis entityToDTO(Restaurant restaurant);


}
