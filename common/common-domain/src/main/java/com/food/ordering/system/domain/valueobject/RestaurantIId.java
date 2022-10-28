package com.food.ordering.system.domain.valueobject;

import java.util.UUID;

public class RestaurantIId extends BaseId<UUID> {
    protected RestaurantIId(UUID value) {
        super(value);
    }
}
