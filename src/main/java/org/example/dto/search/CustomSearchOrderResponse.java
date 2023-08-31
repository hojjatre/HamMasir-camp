package org.example.dto.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomSearchOrderResponse {
    private SearchOrderRestaurant restaurant;
    private SearchOrderUser user_order;
    private Date date;
    private String description;
    private List<SearchOrderFoodDetails> food;
    private Double totalCost;
}
