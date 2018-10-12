package com.example.demo.algorithm.datastructure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ransahojie
 * @Description: math
 * @date 2018/9/30 17:13
 */
public class Math {

    /**
     * Fizz Buzz
     *
     * @param n
     * @return
     */
    public List<String> fizzBuzz(int n) {
        List<String> l = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            if (i % 15 == 0) l.add("FizzBuzz");
            else if (i % 3 == 0) l.add("Fizz");
            else if (i % 5 == 0) l.add("Buzz");
            else l.add(String.valueOf(i));
        }

        return l;
    }
}
