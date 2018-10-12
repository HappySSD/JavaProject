package com.example.demo.algorithm.datastructure;

import java.util.Random;

/**
 * @author ransahojie
 * @Description: design
 * @date 2018/9/30 14:28
 */
public class ShuffleArray {
    private int[] a;

    public ShuffleArray(int[] nums) {
        a = nums;
    }

    /**
     * Resets the array to its original configuration and return it.
     */
    public int[] reset() {
        return a;
    }

    /**
     * Returns a random shuffling of the array.
     */
    public int[] shuffle() {
        int len = a.length;
        Random random = new Random();

        for (int i = len - 1; i > 0; i--) {
            int r = random.nextInt(i + 1);
            int tmp = a[r];
            a[r] = a[i];
            a[i] = tmp;
        }

        return a;
    }
}
