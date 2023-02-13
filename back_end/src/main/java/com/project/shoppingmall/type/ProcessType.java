package com.project.shoppingmall.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum ProcessType {
    READY("준비중"),
    DONE("처리완료"),
    CANCEL("취소");

    private final String name;
}
