package com.example.demo.algorithm.datastructure;

import java.lang.Math;
/**
 * @author ransahojie
 * @Description: DynamicProgram
 * @date 2018/9/29 16:07
 */
public class DynamicProgram {

    /**
     * 爬楼梯
     * 考虑最后一步 跨1阶 跨2阶
     * dp(n) = k dp(n) = dp(n-1) + dp(n-2) dp(1)=1 dp(2)=2
     *
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        //从上往下递归 会有重复计算
//        if (n == 1) return 1;
//        if (n == 2) return 2;
//        return climbStairs(n - 1) + climbStairs(n - 2);

        //从底往上递推 空间换时间 保存中间结果避免重复计算
        int[] m = {1, 1};
        for (int i = 1; i < n; i++) {
            int tmp = m[1];
            m[1] += m[0];
            m[1] = tmp;
        }

        return m[1];
    }

    /**
     * 买卖股票的最佳时机
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int len = prices.length;

        if (len == 0 || len == 1) return 0;

        int minPrice = prices[0];
        int maxProfile = 0;

        for (int i = 1; i < len; i++) {
            minPrice = minPrice > prices[i] ? prices[i] : minPrice;
            maxProfile = Math.max(maxProfile, prices[i] - minPrice);
        }

        return maxProfile;
    }

    /**
     * 最大子序和
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int res = Integer.MIN_VALUE;
        int ans = 0;
        int len = nums.length;

        for (int i = 0; i < len; i++) {
            if (ans < 0) ans = 0;
            ans += nums[i];
            res = Math.max(res, ans);
        }

        return res;
    }

    /**
     * 打家劫舍
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        int len = nums.length;
        if (len == 0) return 0;

        int[] res = {0, nums[0]};
        for (int i = 1; i < len; i++) {
            int tmp = res[1];
            res[1] = Math.max(res[1], res[0] + nums[i]);
            res[0] = tmp;
        }

        return res[1];
    }
}
