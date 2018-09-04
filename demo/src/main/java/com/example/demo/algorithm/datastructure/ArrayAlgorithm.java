package com.example.demo.algorithm.datastructure;

/**
 * @author ransahojie
 * @Description: 数组
 * @date 2018/9/4 15:13
 */
public class ArrayAlgorithm {

    /**
     * 有序数组去重
     * @param arr
     * @return
     */
    public int removeDuplicates(int[] arr) {
        int i = 0;
        int j;
        int n = arr.length;
        if (n <= 1) {
            return n;
        }

        for (j = 1; j < n; j++) {
            if (arr[i] != arr[j]) {
                i++;
                arr[i] = arr[j];
            }
        }

        return i + 1;
    }
}
