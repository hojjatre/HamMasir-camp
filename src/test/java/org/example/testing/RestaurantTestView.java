package org.example.testing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestaurantTestView {
    @JsonProperty("name")
    String name;

    @JsonProperty("ownerName")
    String ownerName;

    @JsonProperty("food")
    String food;

}
