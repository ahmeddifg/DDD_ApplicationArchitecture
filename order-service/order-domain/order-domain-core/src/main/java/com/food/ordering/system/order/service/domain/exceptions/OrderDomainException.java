package com.food.ordering.system.order.service.domain.exceptions;

import com.food.ordering.system.domain.exception.DomainException;

public class OrderDomainException extends DomainException {
    public OrderDomainException(String message) {
        super(message);
    }

    public OrderDomainException(String message, Throwable throwable) {
        super(message, throwable);
    }
}