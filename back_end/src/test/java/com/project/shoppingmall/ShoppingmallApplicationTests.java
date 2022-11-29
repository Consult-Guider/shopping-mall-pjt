package com.project.shoppingmall;

import com.project.shoppingmall.utils.SetProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SetProfile
class ShoppingmallApplicationTests {

    @Test
    @DisplayName("[정상 구동][서버 구동 여부]해당 Profile로 작동이 가능한지 여부만 체크")
    void runTest() {

    }
}
