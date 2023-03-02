package com.project.shoppingmall.utils;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class RandomUtils {
    public static <E> List<E> sample(List<E> list, int size) {
        java.util.Random random = new java.util.Random();
        int size_ = min(size, list.size());

        List<Integer> set = new ArrayList<>();

        while (set.size() < size_) {
            int idx = random.nextInt(list.size());
            if(!set.contains(idx)) {
                set.add(idx);
            }
        }
        return set.stream().map(list::get).toList();
    }
}
