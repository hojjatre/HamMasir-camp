package org.example.testing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderTestView {

    @JsonProperty("totalCost")
    int totalCost;

    @JsonProperty("foods")
    String foods;

    @JsonProperty("restaurantName")
    String restaurantName;

    @JsonProperty("restaurantLocation")
    String restaurantLocation;

    @JsonProperty("userName")
    String userName;

    @JsonProperty("description")
    String description;
}
