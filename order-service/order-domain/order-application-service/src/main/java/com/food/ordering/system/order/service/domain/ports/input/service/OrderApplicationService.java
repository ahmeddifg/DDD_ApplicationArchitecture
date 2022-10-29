package com.food.ordering.system.order.service.domain.ports.input.service;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQueryResponse;

import javax.validation.Valid;

public interface OrderApplicationService {

    CreateOrderResponse CreateOrder(@Valid CreateOrderCommand createOrderCommand);

    TrackOrderQueryResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
