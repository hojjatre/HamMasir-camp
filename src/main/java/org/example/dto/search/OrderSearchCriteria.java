package org.example.dto.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderSearchCriteria {
    private String username;
    private Long restaurantId;
    private Integer totalCost;
}
