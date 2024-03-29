package com.project.shoppingmall.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public enum ImageType {
    MAIN("main"),
    DESC("desc"),
    AD_BANNER("banner");

    private final String name;

    public String getName(int idx) {
        return String.format("%s%s", name, idx);
    }
}
