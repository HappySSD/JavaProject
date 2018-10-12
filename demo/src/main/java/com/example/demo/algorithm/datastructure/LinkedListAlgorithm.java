package com.example.demo.algorithm.datastructure;

import com.fasterxml.jackson.databind.util.LinkedNode;
import jdk.nashorn.internal.ir.ReturnNode;

import java.util.List;
import java.util.spi.LocaleServiceProvider;

/**
 * @author ransahojie
 * @Description: TODO
 * @date 2018/9/18 14:16
 */
public class LinkedListAlgorithm {

    /**
     * 删除链表的倒数第N个节点
     *
     * @param head 头结点
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode first = head;

        while (n-- > 0) {
            first = first.next;
        }

        //链表长度为n
        if (first == null) {
            return head.next;
        }

        ListNode second = head;
        while (first.next != null) {
            first = first.next;
            second = second.next;
        }

        second.next = second.next.next;

        return head;
    }

    /**
     * 反转链表
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }

        return pre;
    }

    /**
     * 合并两个有序链表
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        //处理边界情况
        if (l1 == null) {
            return l2;
        }

        if (l2 == null) {
            return l1;
        }

        ListNode l;
        if (l1.val < l2.val) {
            l = l1;
            l.next = mergeTwoLists(l1.next, l2);
        } else {
            l = l2;
            l.next = mergeTwoLists(l1, l2.next);
        }

        return l;
    }

    /**
     * 回文链表
     *
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return true;
        }

        ListNode middle = getMiddle(head);
        middle.next = reverse(middle.next);

        ListNode p = middle.next;
        while (head != null && p != null && head.val == p.val) {
            head = head.next;
            p = p.next;
        }

        return p == null;
    }

    /**
     * 翻转链表
     *
     * @param head
     * @return
     */
    private ListNode reverse(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        return pre;
    }

    /**
     * 获取链表中间节点
     * 快慢指针 快指针每次移动2位 慢指针每次移动一位
     *
     * @param head
     * @return
     */
    private ListNode getMiddle(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /**
     * 环形链表
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }

        return false;
    }
}
