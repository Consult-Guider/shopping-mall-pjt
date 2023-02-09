package com.project.shoppingmall.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum ProcessType {
    RECEIVED("접수확인"),
    READY("준비중"),
    ONGOING("진행중"),
    DONE("처리완료");

    private final String name;
}
