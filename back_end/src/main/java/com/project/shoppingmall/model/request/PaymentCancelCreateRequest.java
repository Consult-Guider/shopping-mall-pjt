package com.project.shoppingmall.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor @Getter @Setter
public class PaymentCancelCreateRequest {
    private List<String> itemIds;
}
