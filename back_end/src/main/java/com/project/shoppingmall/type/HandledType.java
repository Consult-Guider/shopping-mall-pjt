package com.project.shoppingmall.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum HandledType {
    PURCHASE("PURCHASE"), RECALL("RECALL");

    private final String name;
}
