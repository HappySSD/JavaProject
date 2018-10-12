package com.example.demo.algorithm.datastructure;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.text.StrBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.python.antlr.ast.Str;

import java.util.*;

/**
 * @author ransahojie
 * @Description: 数组
 * @date 2018/9/4 15:13
 */
@Slf4j
public class StringAlgorithm {
    /**
     * 翻转字符串
     *
     * @param s
     * @return
     */
    public String reverseString(String s) {
        char[] chars = s.toCharArray();
        int start = 0;
        int end = chars.length - 1;
        while (start < end) {
            char tmp = chars[start];
            chars[start] = chars[end];
            chars[end] = tmp;
            start++;
            end--;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            sb.append(chars[i]);
        }

        return sb.toString();
    }

    @Test
    public void testReverseString() {
        String a = this.reverseString("hello");
        Assert.assertEquals("olleh", a);
    }

    /**
     * 翻转整数
     *
     * @param x
     * @return
     */
    public int reverse(int x) {
        long value = 0;
        while (x != 0) {
            value = value * 10 + x % 10;
            x = x / 10;
        }

        if (value > Integer.MAX_VALUE || value < Integer.MIN_VALUE) {
            return 0;
        }

        return (int) value;
    }

    /**
     * 字符串中的第一个唯一字符
     * 字符串重复问题-->位图法
     * 建立一个长度为元素范围的数组 元素的值作为数组的下标 遍历集合
     * 当前元素的下标-->当前元素的值-->标记数组的下标-->标记数组的值(元素出现的次数)
     *
     * @param s
     * @return
     */
    public int firstUniqChar(String s) {
        int[] value = new int[256];

        for (int i = 0; i < s.length(); i++) {
            value[s.charAt(i)]++;
        }

        for (int i = 0; i < s.length(); i++) {
            if (value[s.charAt(i)] == 1) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 有效的字母异位词
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isAnagram(String s, String t) {

        if (s.length() != t.length()) {
            return false;
        }

        int[] a = new int[26];
        for (int i = 0; i < s.length(); i++) {
            a[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < t.length(); i++) {
            if (a[t.charAt(i) - 'a'] == 0) {
                return false;
            }
            a[t.charAt(i) - 'a']--;
        }

        return true;
    }

    @Test
    public void testIsAnagram() {
        boolean a = this.isAnagram("a", "b");
        Assert.assertFalse(a);
    }

    /**
     * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
     *
     * @param s
     * @return
     */
    public boolean isPalindrome(String s) {
        int start = 0;
        int end = s.length() - 1;

        while (start < end) {
            char sChar = s.charAt(start);
            char eChar = s.charAt(end);

            if (!isValidChar(sChar)) {
                start++;
            } else if (!isValidChar(eChar)) {
                end--;
            } else if (toUpperCase(sChar) == toUpperCase(eChar)) {
                start++;
                end--;
            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * 比较两个字符
     *
     * @param c
     * @return
     */
    private int toUpperCase(char c) {
        int s = 'a' - 'A';

        if (c >= 'a' && c <= 'z') {
            return c - s;
        }

        return c;
    }

    /**
     * 判断是不是字母数字字符
     *
     * @param c
     * @return
     */
    private boolean isValidChar(char c) {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9';
    }

    @Test
    public void testIsPalindrome() {
        boolean a = this.isPalindrome("A9 man a plan a canal: Panam9a");
        Assert.assertTrue(a);
    }

    /**
     * 字符串转整数（atoi）
     *
     * @param str
     * @return
     */
    public int myAtoi(String str) {
        int value = 0;
        int sign = 1;
        int i = 0;
        int n = str.length();

        if (n == 0) {
            return 0;
        }

        //去除空白字符
        while (i < n && str.charAt(i) == ' ') {
            i++;
        }

        if (i == n) {
            return 0;
        }

        if (str.charAt(i) == '-' || str.charAt(i) == '+') {
            sign = str.charAt(i) == '+' ? 1 : -1;
            i++;
        }

        while (i < n && str.charAt(i) <= '9' && str.charAt(i) >= '0') {

            if (value > Integer.MAX_VALUE / 10 || value == Integer.MAX_VALUE / 10 && (str.charAt(i) - '0' > 7)) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            value = value * 10 + str.charAt(i) - '0';
            i++;
        }

        return value * sign;
    }

    @Test
    public void testMyAtoi() {
        int a = this.myAtoi(" +9");
        Assert.assertEquals(a, 9);
    }

    /**
     * 实现strStr()
     *
     * @param haystack
     * @param needle
     * @return
     */
    public int strStr(String haystack, String needle) {
        char[] t = haystack.toCharArray();

        char[] p = needle.toCharArray();

        int i = 0; // 主串的位置

        int j = 0; // 模式串的位置

        while (i < t.length && j < p.length) {

            if (t[i] == p[j]) { // 当两个字符相同，就比较下一个

                i++;

                j++;

            } else {

                i = i - j + 1; // 一旦不匹配，i后退

                j = 0; // j归0

            }

        }

        if (j == p.length) {

            return i - j;

        } else {

            return -1;

        }
    }

    @Test
    public void testStrStr() {
        int a = this.strStr("mississippi", "issip");
        Assert.assertEquals(a, 4);
    }

    /**
     * 报数
     * 边界情况 递推的规律 从底往上递推 找到第n项
     * 要清楚的确定每一层循环的作用
     * 如果想要从头到尾遍历一个序列 用for循环 如果想要遍历过程中达到某个或多个条件就break掉 用while循环
     *
     * @param n
     * @return
     */
    public String countAndSay(int n) {
        if (n == 1) return "1";

        String result = "1";
        //每一次转化
        while (--n > 0) {
            //存储本次转化结果
            StringBuffer sb = new StringBuffer();
            //遍历当前报数序列
            for (int i = 0; i < result.length(); i++) {
                //存储最长连续相同的个数
                int count = 1;
                //穷举出最长连续项
                while (i + 1 < result.length() && result.charAt(i) == result.charAt(i + 1)) {
                    count++;
                    i++;
                }
                sb.append(count).append(result.charAt(i));
            }
            result = sb.toString();
        }

        return result;
    }

    @Test
    public void testCountAndSay() {
        String a = this.countAndSay(5);
        Assert.assertEquals(a, "111221");
    }

    /**
     * 最长公共前缀
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {

        if(strs.length==0){
            return "";
        }

        if (strs.length==1) {
            return strs[0];
        }

        String first = strs[0];
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < first.length(); i++) {
            for (String str : strs) {
                if (str.length() == i || str.charAt(i) != first.charAt(i)) {
                    return sb.toString();
                }
            }

            sb.append(first.charAt(i));
        }

        return sb.toString();
    }
}
