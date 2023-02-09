package com.project.shoppingmall.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class HandledItemReadStatisticResponse {
    private long countDeliveryByReady;
    private long countDeliveryByOnGoing;
    private long countDeliveryByDONE;
}
