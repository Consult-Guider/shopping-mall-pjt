package com.project.shoppingmall.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class RandomUtilsTest {
    @Test
    void givenList_whenCallSample_thenRandomList() {
        // given
        List<String> list = List.of("A", "B", "C", "D", "E", "F");
        int size = 2;

        // when
        List<String> sample = RandomUtils.sample(list, size);

        // then
        assertThat(sample.size()).isEqualTo(size);
        sample.forEach(log::debug);
    }

    @Test
    void givenListAndTooMuchSize_whenCallSample_thenRandomList() {
        // given
        List<String> list = List.of("A", "B", "C", "D", "E", "F");
        int size = 10000;

        // when
        List<String> sample = RandomUtils.sample(list, size);

        // then
        assertThat(sample.size()).isEqualTo(list.size());
        sample.forEach(log::debug);
    }

}