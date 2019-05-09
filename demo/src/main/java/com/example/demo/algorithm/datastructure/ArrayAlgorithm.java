package com.example.demo.algorithm.datastructure;

import com.alibaba.fastjson.JSON;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author ransahojie
 * @Description: 数组
 * @date 2018/9/4 15:13
 */
@Slf4j
public class ArrayAlgorithm {

  /**
   * 有序数组去重
   * 双指针法 i->新数组最后一个元素 j->遍历整个数组
   * 相同j往后走 不同i移一位.
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

  @Test
  public void testRemoveDuplicates() {
    int len = this.removeDuplicates(new int[]{0, 0, 0, 1, 1, 2, 2, 3, 4});
    Assert.assertEquals(5, len);
  }


  /**
   * 股票最大收益.
   */
  public int maxProfit(int[] prices) {
    int len = prices.length;
    int benefit = 0;
    int minBuy = prices[0];

    if (len < 2) {
      return 0;
    }

    for (int i = 1; i < len; i++) {
      minBuy = Math.min(minBuy, prices[i]);
      benefit = Math.max(benefit, prices[i] - minBuy);
    }

    return benefit;
  }

  @Test
  public void testMaxProfit() {
    int len = this.maxProfit(new int[]{1, 2, 5, 6, 9});
    Assert.assertEquals(8, len);
  }

  /**
   * 钱币找零
   * 贪心算法 每次都选最大面值.
   *
   * @param money 钱数
   * @param value 面值数组
   * @param count 各个面值数量数组
   * @return 个面值实际使用数量
   */
  public int[] change(int money, int[] value, int[] count) {
    int[] result = new int[value.length];

    for (int i = value.length - 1; i < 0; i--) {
      int c = Math.min(money / value[i], count[i]);
      money = money - value[i] * c;
      result[i] = c;
    }

    return result;
  }

  @Test
  public void testChange() {
    int[] result = this
        .change(319, new int[]{1, 5, 10, 20, 50, 100}, new int[]{10, 20, 10, 10, 10, 10});
    Assert.assertArrayEquals(new int[]{4, 1, 1, 0, 0, 3}, result);
  }

  /**
   * 旋转数组
   */
  public int[] rotateArray(int[] nums, int k) {
    reverse(0, nums.length - k - 1, nums);
    reverse(nums.length - k, nums.length - 1, nums);
    reverse(0, nums.length - 1, nums);
    return nums;
  }

  /**
   * 反转一个数组.
   */
  private void reverse(int start, int end, int[] nums) {
    while (start < end) {
      int tmp = nums[start];
      nums[start] = nums[end];
      start++;
      nums[end] = tmp;
      end--;
    }
  }

  @Test
  public void testRotateArray() {
    int[] result = this.rotateArray(new int[]{1, 2, 3}, 2);
    Assert.assertArrayEquals(new int[]{2, 3, 1}, result);
  }

  /**
   * 找出数组中只出现一次的数字
   * 亦或满足交换律交换律.
   * A xor B xor B xor A xor C == (A xor A) xor (B xor B) xor C
   * A xor A==0
   * 0 xor A==A
   */
  public int singleNumber(int[] nums) {
    int tmp = 0;

    for (int num : nums) {
      tmp ^= num;
    }

    return tmp;
  }

  /**
   * 两个数组的交集 II.
   */
  public int[] intersect(int[] nums1, int[] nums2) {
    List<Integer> tmp = new ArrayList<>();

    Map<Integer, Integer> map = new HashMap<Integer, Integer>();

    for (int i = 0; i < nums1.length; i++) {
      Integer value = map.get(nums1[i]);
      map.put(nums1[i], (value == null ? 0 : value) + 1);
    }

    for (int i = 0; i < nums2.length; i++) {
      if (map.containsKey(nums2[i]) && map.get(nums2[i]) != 0) {
        tmp.add(nums2[i]);
        map.put(nums2[i], map.get(nums2[i]) - 1);
      }
    }

    int[] result = new int[tmp.size()];
    int i = 0;
    for (Integer e : tmp) {
      result[i++] = e;
    }
    return result;
  }

  /**
   * 两个数组的交集 II.
   */
  public int[] intersect2(int[] nums1, int[] nums2) {
    Arrays.sort(nums1);
    Arrays.sort(nums2);
    List<Integer> tmp = new ArrayList<>();
    int i = 0;
    int j = 0;

    while (i < nums1.length && j < nums2.length) {
      if (nums1[i] > nums2[j]) {
        j++;
      } else if (nums1[i] < nums2[j]) {
        i++;
      } else {
        tmp.add(nums1[i]);
        i++;
        j++;
      }
    }

    int k = 0;
    int[] result = new int[tmp.size()];
    for (int num : tmp) {
      result[k] = num;
      k++;
    }
    return result;
  }

  /**
   * 加一
   * 末尾非9 末尾是9倒数第二位非9 末尾是9倒数第二位是9不全为9 全为9
   * 遇9置0 非9加1 全为9 拓展数组.
   */
  public int[] plusOne(int[] digits) {
    int len = digits.length;

    for (int i = len - 1; i >= 0; i--) {
      if (digits[i] < 9) {
        digits[i] += 1;
        return digits;
      }

      digits[i] = 0;
    }

    int[] result = new int[len + 1];
    result[0] = 1;
    return result;
  }

  /**
   * 把0移动数组末尾.
   */
  public int[] moveZeroes(int[] nums) {
    int fast = 0, slow = 0;
    int n = nums.length;

    while (fast < n) {
      if (nums[fast] != 0) {
        nums[slow] = nums[fast];
        slow++;
      }
      fast++;
    }

    for (int i = slow; i < n; i++) {
      nums[i] = 0;
    }

    return nums;
  }

  @Test
  public void testMoveZeroes() {
    int[] nums = this.moveZeroes(new int[]{1, 0, 0, 2, 0, 3});
    log.info(JSON.toJSONString(nums));
    Assert.assertArrayEquals(new int[]{1, 2, 3, 0, 0, 0}, nums);
  }

  /**
   * 两数之和.
   */
  public int[] twoSum(int[] nums, int target) {

    if (nums.length < 2) {
      throw new RuntimeException();
    }

    Map<Integer, Integer> map = new HashMap<>();

    for (int i = 0; i < nums.length; i++) {
      if (map.containsKey(nums[i])) {
        return new int[]{map.get(nums[i]), i};
      }
      int tmp = target - nums[i];
      map.put(tmp, i);
    }

    throw new RuntimeException();
  }

  @Test
  public void testTowSum() {
    int[] nums = this.twoSum(new int[]{2, 7, 11, 15}, 9);
    log.info(JSON.toJSONString(nums));
    Assert.assertArrayEquals(new int[]{0, 1}, nums);
  }

  /**
   * 旋转图像
   * 沿对角线交换 再沿着中竖轴交换.
   */
  public void rotate(int[][] matrix) {

    int n = matrix.length;

    //沿对角线旋转 [i,j]-->[j,i]
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        matrix[i][j] = matrix[i][j] ^ matrix[j][i];
        matrix[j][i] = matrix[i][j] ^ matrix[j][i];
        matrix[i][j] = matrix[i][j] ^ matrix[j][i];
      }
    }

    //沿着中竖线旋转  [i,j]-->[i,n-j-1]
    //交换ab值 a=a^b b=a^b a=a^b
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n / 2; j++) {
        matrix[i][j] = matrix[i][j] ^ matrix[i][n - j - 1];
        matrix[i][n - j - 1] = matrix[i][j] ^ matrix[i][n - j - 1];
        matrix[i][j] = matrix[i][j] ^ matrix[i][n - j - 1];
      }
    }
  }

  /**
   * 判断一个 9x9 的数独是否有效
   * <p>
   * 位图法 bitmap 用位表示数据的状态 最大数/8 表示需要的字节数
   * <p>
   * 分三步 行判重 列判重 宫判重  每次都是两层for循环判断 只不过可以合在一起 每一种判重循环的含义是不一样的
   * 行判重 外层是每一行 内层是行内每个元素 ... 宫判重 外层是每一个宫 内层是宫内每一个格
   * 每次取数据时是从原矩阵里面取 要考虑到 索引对应关系
   */
  public boolean isValidSudoku(char[][] board) {
    for (int i = 0; i < 9; i++) {
      //设置标志数组
      char[] row = new char[9];
      char[] col = new char[9];
      char[] cube = new char[9];

      for (int j = 0; j < 9; j++) {
        //行判重
        if (board[i][j] != '.') {
          if (row[board[i][j] - '1'] == 1) {
            return false;
          } else {
            row[board[i][j] - '1'] = 1;
          }
        }

        //列判重 i表示列 j表示列中每一个元素 对应原矩阵应该是board[j,i]
        if (board[j][i] != '.') {
          if (col[board[j][i] - '1'] == 1) {
            return false;
          } else {
            col[board[j][i] - '1'] = 1;
          }
        }

        //第i个宫 第j个格 在整个矩阵的坐标
        // int RowIndex = 3 * (i / 3) + j / 3;
        // int ColIndex = 3 * (i % 3) + j % 3;
        int value = board[3 * (i / 3) + j / 3][3 * (i % 3) + j % 3];
        //宫判重
        if (value != '.') {
          if (cube[value - '1'] == 1) {
            return false;
          } else {
            cube[value - '1'] = 1;
          }
        }
      }
    }

    return true;
  }

  /**
   * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0?
   * Find all unique triplets in the array which gives the sum of zero.
   */
  public List<List<Integer>> threeSum(int[] nums) {
    if (nums == null || nums.length < 3) {
      return Collections.emptyList();
    }
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(nums);
    for (int i = 0; i < nums.length - 2; i++) {
      if (i > 0 && nums[i] == nums[i - 1]) {
        continue;
      }
      int left = i + 1, right = nums.length - 1;
      int target = -nums[i];
      towSum(nums, left, right, result, target);
    }
    return result;
  }

  private void towSum(int[] nums, int left, int right, List<List<Integer>> result, int target) {
    while (left < right) {
      if (nums[left] + nums[right] == target) {
        List<Integer> list = new ArrayList<>();
        list.add(-target);
        list.add(nums[left]);
        list.add(nums[right]);
        result.add(list);

        left++;
        right--;

        while (left < right && nums[left] == nums[left - 1]) {
          left++;
        }
        while (left < right && nums[right] == nums[right + 1]) {
          right--;
        }
      } else if (nums[left] + nums[right] < target) {
        left++;
      } else {
        right--;
      }
    }
  }

  @Test
  public void testThreeSum() {
    List<List<Integer>> result = this.threeSum(new int[]{-2, 0, 1, 1, 2});
    log.info(JSON.toJSONString(result));
  }

  public List<List<String>> groupAnagrams(String[] strs) {
    if (strs == null || strs.length == 0) {
      return Collections.emptyList();
    }
    Map<String, List<String>> map = new HashMap<>();
    for (int i = 0; i < strs.length; i++) {
      char[] strCharArr = strs[i].toCharArray();
      Arrays.sort(strCharArr);
      String sortedStr = String.valueOf(strCharArr);
      map.putIfAbsent(sortedStr, new ArrayList<>());
      map.get(sortedStr).add(strs[i]);
    }
    return new ArrayList<>(map.values());
  }

  @Test
  public void testGroupAnagrams() {
    List<List<String>> result = this
        .groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});
    log.info(JSON.toJSONString(result));
  }

  /**
   * Given a string, find the length of the longest substring without repeating characters.
   * fast slow pointer
   */
  public int lengthOfLongestSubstring(String s) {
    if (s == null || s == "") {
      return 0;
    }
    int max = 0;
    int slow = 0;
    Map<Character, Integer> map = new HashMap<>();
    for (int i = 0; i < s.length(); i++) {
      if (map.containsKey(s.charAt(i))) {
        slow = Math.max(map.get(s.charAt(i)) + 1, slow);
      }
      max = Math.max(max, i - slow + 1);
      map.put(s.charAt(i), i);
    }
    return max;
  }

  @Test
  public void testLengthOfLongestSubstring() {
    int result = this.lengthOfLongestSubstring("abba");
    log.info(JSON.toJSONString(result));
  }

  /**
   * Given a string s, find the longest palindromic substring in s. You may assume that the maximum
   * length of s is 1000.
   */
  public String longestPalindrome(String s) {
    if (s == null || s == "") {
      return "";
    }
    int len = s.length();
    int start = 0;
    int maxLen = 0;
    boolean[][] sign = new boolean[len][len];
    for (int i = 0; i < len; i++) {
      for (int j = 0; j <= i; j++) {
        if (i == j) {
          sign[j][i] = true;
        } else if (i - j == 1) {
          sign[j][i] = s.charAt(j) == s.charAt(i);
        } else {
          sign[j][i] = (s.charAt(j) == s.charAt(i)) && sign[j + 1][i - 1];
        }
        if (sign[j][i] && (i - j + 1) > maxLen) {
          start = j;
          maxLen = i - j + 1;
        }
      }
    }
    return s.substring(start, start + maxLen);
  }

  @Test
  public void testLongestPalindrome() {
    String result = this.longestPalindrome("level");
    log.info(JSON.toJSONString(result));
  }

  /**
   * Given an unsorted array return whether an increasing subsequence of length 3 exists or not in
   * the array.
   * three pointer
   */
  public boolean increasingTriplet(int[] nums) {
    if (nums == null || nums.length < 3) {
      return false;
    }
    int minValue = Integer.MAX_VALUE;
    int midValue = Integer.MAX_VALUE;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] <= minValue) {
        minValue = nums[i];
      } else if (nums[i] <= midValue) {
        midValue = nums[i];
      } else {
        return true;
      }
    }
    return false;
  }

  @Test
  public void testIncreasingTriplet() {
    boolean result = this.increasingTriplet(new int[]{1, 1, 1, 1, 1});
    log.info(JSON.toJSONString(result));
  }

  /**
   * 合并两个有序数组
   * 从后往前合并到一个数组.
   */
  public void merge(int[] nums1, int m, int[] nums2, int n) {
    while (m >= 0 && n >= 0) {
      if (n == 0) {
        return;
      }

      if (m == 0 && n > 0) {
        for (int i = 0; i < n; i++) {
          nums1[i] = nums2[i];
        }
        return;
      }

      if (nums1[m - 1] > nums2[n - 1]) {
        nums1[m + n - 1] = nums1[m - 1];
        m--;
      } else {
        nums1[m + n - 1] = nums2[n - 1];
        n--;
      }
    }
  }

  /**
   * 第一个错误的版本.
   */
  public int firstBadVersion(int n) {
    int left = 1, right = n;
    while (left < right) {
      int mid = left + (right - left) / 2;
      if (isBadVersion(mid)) {
        right = mid;
      } else {
        left = mid + 1;
      }
    }
    return right;
  }

  /**
   * 判断是否错误.
   */
  private boolean isBadVersion(int mid) {
    return true;
  }

  @Test
  public void testArray() {
    int[] arr = new int[5];
    String result = JSON.toJSONString(arr);
    int[] parse = JSON.parseObject(result, int[].class);
    log.info(JSON.toJSONString(parse));
  }

  public int findPoisonedDuration(int[] timeSeries, int duration) {
    if (timeSeries == null || timeSeries.length == 0) {
      return 0;
    }
    int total = 0;
    for (int i = 0; i < timeSeries.length; i++) {
      int end = timeSeries[i] + duration;
      if ((i + 1) < timeSeries.length) {
        if (timeSeries[i + 1] > end) {
          total += duration;
        } else {
          total += timeSeries[i + 1] - timeSeries[i];
        }
      } else {
        total += duration;
      }
    }
    return total;
  }

  public int majorityElement(int[] nums) {
    if (nums == null || nums.length == 0) {
      return -1;
    }
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      if (map.containsKey(nums[i])) {
        map.put(nums[i], map.get(nums[i]) + 1);
      } else {
        map.put(nums[i], 1);
      }
    }
    for (Map.Entry entry : map.entrySet()) {
      if ((Integer) entry.getValue() > nums.length / 2) {
        return (Integer) entry.getKey();
      }
    }
    return -1;
  }

  @Test
  public void testMajorityElement() {
    int result = this.majorityElement(new int[]{3, 2, 3});
    log.info(JSON.toJSONString(result));
  }

  /**
   * Given an array nums of n integers and an integer target, find three integers in nums such that
   * the sum is closest to target. Return the sum of the three integers. You may assume that each
   * input would have exactly one solution.
   */
  public int threeSumClosest(int[] nums, int target) {
    if (nums == null || nums.length < 3) {
      return -1;
    }
    Arrays.sort(nums);
    int bestSum = nums[0] + nums[1] + nums[2];
    for (int i = 0; i < nums.length - 2; i++) {
      int start = i + 1;
      int end = nums.length - 1;
      while (start < end) {
        int threeSum = nums[i] + nums[start] + nums[end];
        if (threeSum == target) {
          return target;
        }
        if (Math.abs(threeSum - target) < Math.abs(bestSum - target)) {
          bestSum = threeSum;
        }
        if (threeSum < target) {
          start++;
        }
        if (threeSum > target) {
          end--;
        }
      }
    }
    return bestSum;
  }

  @Test
  public void testThreeSumClosest() {
    int result = this.threeSumClosest(new int[]{0, 1, 2}, 3);
    log.info(JSON.toJSONString(result));
  }

  /**
   * Given an array nums of n integers and an integer target, are there elements a, b, c, and d
   * in nums such that a + b + c + d = target? Find all unique quadruplets in the array which
   * gives the sum of target.
   */
  public List<List<Integer>> fourSum(int[] nums, int target) {
    if (nums == null || nums.length < 4) {
      return Collections.emptyList();
    }
    Arrays.sort(nums);
    List<List<Integer>> result = new ArrayList();
    for (int i = 0; i < nums.length - 3; i++) {
      if (i > 0 && nums[i] == nums[i - 1]) {
        continue;
      }
      for (int j = i + 1; j < nums.length - 2; j++) {
        if (j > i + 1 && nums[j] == nums[j - 1]) {
          continue;
        }
        int l = j + 1;
        int r = nums.length - 1;
        while (l < r) {
          int fourSum = nums[i] + nums[j] + nums[l] + nums[r];
          if (fourSum == target) {
            List<Integer> tmp = new ArrayList();
            tmp.add(nums[i]);
            tmp.add(nums[j]);
            tmp.add(nums[l]);
            tmp.add(nums[r]);
            result.add(tmp);
            l++;
            r--;
            while (l < r && nums[l] == nums[l - 1]) {
              l++;
            }
            while (l < r && nums[r] == nums[r + 1]) {
              r--;
            }
          } else if (fourSum < target) {
            l++;
          } else {
            r--;
          }
        }
      }
    }
    return result;
  }

  @Test
  public void testFourSum() {
    List<List<Integer>> result = this.fourSum(new int[]{0, 0, 0, 0}, 0);
    log.info(JSON.toJSONString(result));
  }
}
