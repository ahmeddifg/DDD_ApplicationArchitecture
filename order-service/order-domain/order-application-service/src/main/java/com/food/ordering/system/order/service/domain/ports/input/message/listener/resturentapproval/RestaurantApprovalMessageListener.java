package com.food.ordering.system.order.service.domain.ports.input.message.listener.resturentapproval;

import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQueryResponse;

public interface RestaurantApprovalMessageListener {

    void orderApproved(TrackOrderQueryResponse trackOrderQueryResponse);
    void orderRejected(TrackOrderQueryResponse trackOrderQueryResponse);
}
