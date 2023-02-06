package com.project.shoppingmall.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum HandledType {
    PAYMENT("PAYMENT"), DELIVERY("DELIVERY");

    private final String name;
}
