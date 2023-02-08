package com.project.shoppingmall.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum HandledType {
    PAYMENT("PAYMENT"), DELIVERY("DELIVERY"); //TODO: 구매확정만 할 예정

    private final String name;
}
