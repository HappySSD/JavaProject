package com.example.demo.algorithm.datastructure;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Auther: ranshaojie
 * @Date: 2018/10/31 11:50
 * @Description:
 */
public class Sort {

    /**
     * bubble sort
     *
     * @param nums
     * @return
     */
    public int[] bubble(int[] nums) {
        int len = nums.length;

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (nums[i] > nums[j]) {
                    int tmp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = tmp;
                }
            }

        }

        return nums;
    }

    @Test
    public void testBubble() {
        int[] nums = bubble(new int[]{1, 5, 2, 7, 3, 1});
        Assert.assertArrayEquals(nums, new int[]{1, 1, 2, 3, 5, 7});
    }

    /**
     * swap
     *
     * @param nums
     * @param i
     * @param j
     */
    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    /**
     * quick sort
     *
     * @param nums
     * @return
     */
    public void quick(int[] nums, int lo, int hi) {
        //递归的边界条件，当 L == R时数组的元素个数为1个
        if (lo < hi) {
            int base = nums[lo];
            int i = lo+1;
            int j = hi;

            //当i == j时，i和j同时指向的元素还没有与中轴元素判断，
            //小于等于中轴元素，i++,大于中轴元素j--,
            //当循环结束时，一定有i = j+1, 且i指向的元素大于中轴，j指向的元素小于等于中轴
            while (i <= j) {
                while (i <= j && nums[i] <= base) i++;
                while (i <= j && nums[j] >= base) j--;

                //当 i > j 时整个切分过程就应该停止了，不能进行交换操作
                //这个可以改成 i < j， 这里 i 永远不会等于j， 因为有上述两个循环的作用
                if (i <= j) {
                    swap(nums, i, j);
                    i++;
                    j--;
                }
            }

            //当循环结束时，j指向的元素是最后一个（从左边算起）小于等于中轴的元素
            swap(nums, lo, j);

            quick(nums, lo, j - 1);
            quick(nums, j + 1, hi);
        }

    }

    @Test
    public void testQuick() {
        int[] nums = new int[]{1, 5, 2, 7, 3, 1};
        quick(nums,0,nums.length-1);
        Assert.assertArrayEquals(nums, new int[]{1, 1, 2, 3, 5, 7});
    }


}
