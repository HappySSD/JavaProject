package com.example.demo.algorithm.datastructure;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ransahojie
 * @Description: DynamicProgram
 * @date 2018/9/29 16:07
 */
public class DynamicProgram {

  /**
   * 爬楼梯
   * 考虑最后一步 跨1阶 跨2阶
   * dp(n) = k dp(n) = dp(n-1) + dp(n-2) dp(1)=1 dp(2)=2.
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
   * 买卖股票的最佳时机.
   */
  public int maxProfit(int[] prices) {
    int len = prices.length;

    if (len == 0 || len == 1) {
      return 0;
    }

    int minPrice = prices[0];
    int maxProfile = 0;

    for (int i = 1; i < len; i++) {
      minPrice = minPrice > prices[i] ? prices[i] : minPrice;
      maxProfile = Math.max(maxProfile, prices[i] - minPrice);
    }

    return maxProfile;
  }

  /**
   * Given an integer array nums, find the contiguous subarray (containing at least one number)
   * which has the largest sum and return its sum.
   */
  public int maxSubArray(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    int[] dp = new int[nums.length];
    dp[0] = nums[0];
    int res = nums[0];
    for (int i = 1; i < nums.length; i++) {
      dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
      res = Math.max(res, dp[i]);
    }
    return res;
  }

  /**
   * 打家劫舍.
   */
  public int rob(int[] nums) {
    int len = nums.length;
    if (len == 0) {
      return 0;
    }

    int[] res = {0, nums[0]};
    for (int i = 1; i < len; i++) {
      int tmp = res[1];
      res[1] = Math.max(res[1], res[0] + nums[i]);
      res[0] = tmp;
    }

    return res[1];
  }

  /**
   * Given an unsorted array of integers, find the number of longest increasing subsequence.
   * dp[i] = max(dp[i],dp[j]+1:if(a[i]>a[j])) 0<=j<=i-1
   */
  public int findNumberOfLIS(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    int[] dp = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
      dp[i] = 1;
    }
    int res = Integer.MIN_VALUE;
    for (int i = 0; i < nums.length; i++) {
      for (int j = 0; j < i; j++) {
        if (nums[i] > nums[j]) {
          dp[i] = Math.max(dp[i], dp[j] + 1);
          res = Math.max(dp[i], res);
        }
      }
    }
    return res;
  }

  /**
   * Given an integer array nums, find the contiguous subarray within an array (containing at least
   * one number) which has the largest product.
   */
  public int maxProduct(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    int[] dpMax = new int[nums.length];
    int[] dpMin = new int[nums.length];
    dpMax[0] = nums[0];
    dpMin[0] = nums[0];
    int res = nums[0];
    for (int i = 1; i < nums.length; i++) {
      dpMax[i] = Math.max(Math.max(dpMax[i - 1] * nums[i], dpMin[i - 1] * nums[i]), nums[i]);
      dpMin[i] = Math.min(Math.min(dpMax[i - 1] * nums[i], dpMin[i - 1] * nums[i]), nums[i]);
      res = Math.max(dpMax[i], res);
    }
    return res;
  }

  /**
   * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom
   * right which minimizes the sum of all numbers along its path.
   * Note: You can only move either down or right at any point in time.
   */
  public int minPathSum(int[][] grid) {
    if (grid == null || grid.length == 0) {
      return 0;
    }
    int row = grid.length;
    int column = grid[0].length;
    for (int i = 1; i < row; i++) {
      grid[i][0] += grid[i - 1][0];
    }
    for (int j = 1; j < column; j++) {
      grid[0][j] += grid[0][j - 1];
    }
    for (int i = 1; i < row; i++) {
      for (int j = 1; j < column; j++) {
        grid[i][j] = Math.min(grid[i - 1][j], grid[i][j - 1]) + grid[i][j];
      }
    }
    return grid[row - 1][column - 1];
  }

  /**
   * Given a list of non-negative numbers and a target integer k, write a function to check if the
   * array has a continuous subarray of size at least 2 that sums up to the multiple of k, that is,
   * sums up to n*k where n is also an integer.
   */
  public boolean checkSubarraySum(int[] nums, int k) {
    if (nums == null || nums.length == 0) {
      return false;
    }

    int[] prefixSum = new int[nums.length + 1];
    prefixSum[0] = 0;
    for (int i = 1; i < prefixSum.length; i++) {
      prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
    }

    for (int i = 0; i < prefixSum.length; i++) {
      for (int j = i + 2; j < prefixSum.length; j++) {
        int sum = prefixSum[j] - prefixSum[i];
        if (sum == k || (k != 0 && sum % k == 0)) {
          return true;
        }
      }
    }
    return false;
    //    if (nums == null || nums.length == 0) {
    //      return false;
    //    }
    //    if (nums.length == 1) {
    //      return k != 0 && nums[0] >= 2 && nums[0] % k == 0;
    //    }
    //    boolean[] dp = new boolean[nums.length];
    //    if (k != 0 && nums[0] % k == 0) {
    //      dp[0] = true;
    //    }
    //    for (int i = 1; i < nums.length; i++) {
    //      boolean exist = false;
    //      int sum = nums[i];
    //      for (int j = i; j > 0; j--) {
    //        sum += nums[j];
    //        if (sum == 0 && k == 0 || k != 0 && sum % k == 0 && sum >= 2) {
    //          exist = true;
    //          break;
    //        }
    //      }
    //      dp[i] = dp[i - 1] || exist;
    //    }
    //    return dp[nums.length - 1];
  }

  /**
   * Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?
   * dp[n] = dp[0]*dp[n-1]+dp[1]*dp[n-2]+...+dp[n-1]dp[0]
   */
  public int numTrees(int n) {
    int[] dp = new int[n + 1];
    dp[0] = 1;
    dp[1] = 1;
    for (int i = 2; i <= n; i++) {
      for (int j = 0; j < i; j++) {
        dp[i] += dp[j] * dp[i - j - 1];
      }
    }
    return dp[n];
  }

  /**
   * Given an integer n, generate all structurally unique BST's (binary search trees) that store
   * values 1 ... n.
   */
  public List<TreeNode> generateTrees(int n) {
    if (n < 1) {
      return new ArrayList<>();
    }
    return generateTreesHelper(1, n);
  }

  private List<TreeNode> generateTreesHelper(int s, int e) {
    List<TreeNode> result = new ArrayList<>();
    //左子树或又子树为空
    if (s > e) {
      result.add(null);
      return result;
    }
    for (int i = s; i <= e; i++) {
      List<TreeNode> leftTreeList = generateTreesHelper(s, i - 1);
      List<TreeNode> rightTreeList = generateTreesHelper(i + 1, e);
      for (int j = 0; j < leftTreeList.size(); j++) {
        for (int k = 0; k < rightTreeList.size(); k++) {
          TreeNode root = new TreeNode(i);
          root.left = leftTreeList.get(j);
          root.right = rightTreeList.get(k);
          result.add(root);
        }
      }
    }
    return result;
  }

  /**
   * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words,
   * determine if s can be segmented into a space-separated sequence of one or more dictionary
   * words.
   * Note:
   * The same word in the dictionary may be reused multiple times in the segmentation.
   * You may assume the dictionary does not contain duplicate words.
   */
  public boolean wordBreak(String s, List<String> wordDict) {
    if (s == null || "".equals(s)) {
      return false;
    }
    int len = s.length();
    //dp[i] represent whether s[0...i] can be formed by the wordDict
    boolean[] dp = new boolean[len];
    for (int i = 0; i < len; i++) {
      for (int j = 0; j <= i; j++) {
        String sub = s.substring(j, i + 1);
        if (wordDict.contains(sub) && (j == 0 || dp[j - 1])) {
          dp[i] = true;
          break;
        }
      }
    }
    return dp[len - 1];
  }

  /**
   * You are a professional robber planning to rob houses along a street. Each house has a
   * certain amount of money stashed. All houses at this place are arranged in a circle. That
   * means the first house is the neighbor of the last one. Meanwhile, adjacent houses have
   * security system connected and it will automatically contact the police if two adjacent
   * houses were broken into on the same night.
   * Given a list of non-negative integers representing the amount of money of each house,
   * determine the maximum amount of money you can rob tonight without alerting the police.
   * 环形DP transform 两趟DP
   */
  public int robWithCircle(int[] nums) {
    if (nums.length == 0) {
      return 0;
    }
    if (nums.length < 2) {
      return nums[0];
    }
    return Math
        .max(doRobWithCircle(nums, 0, nums.length - 2), doRobWithCircle(nums, 1, nums.length - 1));
  }

  private int doRobWithCircle(int[] nums, int l, int r) {
    int n = r - l + 1;
    if (n == 1) {
      return nums[l];
    }
    int[] dp = new int[n];
    dp[0] = nums[l];
    dp[1] = Math.max(nums[l], nums[l + 1]);
    for (int i = 2; i < n; i++) {
      dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[l + i]);
    }
    return dp[n - 1];
  }

  /**
   * Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4,
   * 9, 16, ...) which sum to n.
   * dp[n] = min(dp[n-1*1],dp[n-2*2],dp[n-3*3],...,dp[n-j*j])+1 j*j<i.
   * 如果当前状态需要由之前一系列状态推导出来时 递推中需要再加一层循环获取之前一系列的状态.
   */
  public int numSquares(int n) {
    int[] dp = new int[n + 1];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j * j <= i; j++) {
        dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
      }
    }
    return dp[n];
  }
}
