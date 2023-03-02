package com.project.shoppingmall.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum HandledType {
    PAYMENT("PAYMENT");

    private final String name;
}
