package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.*;
import com.food.ordering.system.order.service.domain.exceptions.OrderDomainException;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;


import java.util.List;
import java.util.UUID;


public class Order extends AggregateRoot<OrderId> {
    private final CustomerId customerId;
    private final RestaurantIId restaurantIId;
    private final StreetAddress streetAddress;
    private final Money price;
    private final List<OrderItem> items;

    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failingMassages;

    public void initializeOrder() {
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializeOrderItems();
    }

    public void validOrder() {
        validateInitialOrder();
        ValidateTotalPrice();
        validateItemsPrice();
    }

    public void pay() {
        if (this.orderStatus.equals(OrderStatus.PENDING))
            throw new OrderDomainException("Can process this Order, This order state is invalid!");

        orderStatus = OrderStatus.PAID;
    }

    public void approve() {
        if (this.orderStatus.equals(OrderStatus.PAID))
            throw new OrderDomainException("Can't process approve on this order,This order is not paid!");

        this.orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel( List<String> failingMassages){
        if(!orderStatus.equals(OrderStatus.PAID))
            throw new OrderDomainException("Can;t cancel,,This order is not paid!");

        this.orderStatus=OrderStatus.CANCELLING;
        updateFailingMassages(failingMassages);
    }

    public void cancel(List<String> failingMassages){
        if(!orderStatus.equals(OrderStatus.PENDING) && !orderStatus.equals(OrderStatus.CANCELLING) )
            throw new OrderDomainException("Can;t cancel,,This order is not paid!");

        this.orderStatus=OrderStatus.CANCELLED;
        updateFailingMassages(failingMassages);
    }

    private void updateFailingMassages(List<String> failingMassages) {
        if(this.failingMassages!=null && failingMassages!=null)
            this.failingMassages.addAll(failingMassages.stream().filter(message-> !message.isEmpty()).toList());

        if(this.failingMassages==null)
            this.failingMassages=failingMassages;
    }


    private void validateItemsPrice() {
        Money orderItemTotal = this.items.stream().map((orderItem) -> {
                    validateItemPrice(orderItem);
                    return orderItem.getSubtotal();
                }
        ).reduce(Money.ZERO, Money::add);
        if (price != orderItemTotal)
            throw new OrderDomainException("Total price is not valid!");
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (!orderItem.validateOrderItem())
            throw new OrderDomainException("Order Item price is not valid!");
    }

    private void ValidateTotalPrice() {
        if (price != null || !price.isGreaterThanZero())
            throw new OrderDomainException("Total price should be greater than Zero");
    }

    private void validateInitialOrder() {
        if (orderStatus != null || getId() != null)
            throw new OrderDomainException("Order is not in correct State initialization!!");
    }

    public void initializeOrderItems() {
        long itemId = 1;
        for (OrderItem item : items) {
            item.initializeOrderItems(super.getId(), new OrderItemId(itemId++));
        }
    }

    private Order(Builder builder) {
        super.setId(builder.orderId);
        customerId = builder.customerId;
        restaurantIId = builder.restaurantIId;
        streetAddress = builder.streetAddress;
        price = builder.price;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failingMassages = builder.failingMassages;
    }


    public CustomerId getCustomerId() {
        return customerId;
    }

    public RestaurantIId getRestaurantIId() {
        return restaurantIId;
    }

    public StreetAddress getStreetAddress() {
        return streetAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailingMassages() {
        return failingMassages;
    }

    public static final class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private RestaurantIId restaurantIId;
        private StreetAddress streetAddress;
        private Money price;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failingMassages;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder restaurantIId(RestaurantIId val) {
            restaurantIId = val;
            return this;
        }

        public Builder streetAddress(StreetAddress val) {
            streetAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failingMassages(List<String> val) {
            failingMassages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
