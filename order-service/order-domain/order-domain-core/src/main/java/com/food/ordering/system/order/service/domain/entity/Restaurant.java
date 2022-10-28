package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.RestaurantIId;

import java.util.List;

public class Restaurant extends AggregateRoot<RestaurantIId> {
    private final List<Product> productList;
    private boolean active;

    private Restaurant(Builder builder) {
        super.setId(builder.restaurantIId);
        productList = builder.productList;
        active = builder.active;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public boolean isActive() {
        return active;
    }

    public static final class Builder {
        private RestaurantIId restaurantIId;
        private List<Product> productList;
        private boolean active;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(RestaurantIId val) {
            restaurantIId = val;
            return this;
        }

        public Builder productList(List<Product> val) {
            productList = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(this);
        }
    }
}
