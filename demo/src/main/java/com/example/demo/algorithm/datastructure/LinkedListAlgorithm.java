package com.example.demo.algorithm.datastructure;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author ransahojie
 * @Description: linked
 * @date 2018/9/18 14:16
 */
@Slf4j
public class LinkedListAlgorithm {

  /**
   * 删除链表的倒数第N个节点.
   *
   * @param head 头结点
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
   * 反转链表.
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
   * 合并两个有序链表.
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
   * 回文链表.
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
   * 翻转链表.
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
   * 快慢指针 快指针每次移动2位 慢指针每次移动一位.
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
   * 环形链表.
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

  /**
   * You are given two non-empty linked lists representing two non-negative integers. The digits
   * are stored in reverse order and each of their nodes contain a single digit. Add the two
   * numbers and return it as a linked list.
   * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
   * Example:
   * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
   * Output: 7 -> 0 -> 8
   * Explanation: 342 + 465 = 807.
   */
  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode cur = new ListNode(0);
    ListNode head = cur;
    int carry = 0;
    while (l1 != null || l2 != null || carry != 0) {
      if (l1 != null) {
        carry += l1.val;
        l1 = l1.next;
      }
      if (l2 != null) {
        carry += l2.val;
        l2 = l2.next;
      }
      ListNode node = new ListNode(carry % 10);
      carry /= 10;
      cur.next = node;
      cur = node;
    }
    return head.next;
  }

  @Test
  public void testAddTwoNumbers() {
    ListNode l1 = new ListNode(2);
    l1.next = new ListNode(4);
    l1.next.next = new ListNode(3);
    ListNode l2 = new ListNode(5);
    l2.next = new ListNode(6);
    l2.next = new ListNode(4);
    ListNode result = this.addTwoNumbers(l1, l2);
    while (result != null) {
      log.info(JSON.toJSONString(result.val));
      result = result.next;
    }
  }

  public ListNode deleteDuplicatesIteration(ListNode head) {
    if (head == null || head.next == null) {
      return head;
    }
    ListNode curr = head;
    while (curr != null && curr.next != null) {
      if (curr.val == curr.next.val) {
        curr.next = curr.next.next;
      } else {
        curr = curr.next;
      }
    }
    return head;
  }

  public ListNode deleteDuplicatesRecursion(ListNode head) {
    if (head == null || head.next == null) {
      return head;
    }
    if (head.val == head.next.val) {
      head = deleteDuplicatesRecursion(head.next);
    } else {
      head.next = deleteDuplicatesRecursion(head.next);
    }
    return head;
  }

  /**
   * Given a singly linked list, group all odd nodes together followed by the even nodes. Please
   * note here we are talking about the node number and not the value in the nodes.
   * You should try to do it in place. The program should run in O(1) space complexity and O
   * (nodes) time complexity.
   * Example 1:
   * Input: 1->2->3->4->5->NULL
   * Output: 1->3->5->2->4->NULL
   * Example 2:
   * Input: 2->1->3->5->6->4->7->NULL
   * Output: 2->3->6->7->1->5->4->NULL
   */
  public ListNode oddEvenList(ListNode head) {
    if (head == null || head.next == null || head.next.next == null) {
      return head;
    }
    ListNode startEven = head.next;
    ListNode odd = head;
    ListNode even = head.next;
    while (odd != null && even != null && even.next != null) {
      odd.next = even.next;
      odd = even.next;
      even.next = odd.next;
      even = odd.next;
    }
    odd.next = startEven;
    return head;
  }

  @Test
  public void testOddEvenList() {
    ListNode l1 = new ListNode(1);
    l1.next = new ListNode(2);
    l1.next.next = new ListNode(3);
    l1.next.next.next = new ListNode(4);
    l1.next.next.next.next = new ListNode(5);
    l1.next.next.next.next.next = new ListNode(6);
    l1.next.next.next.next.next.next = new ListNode(7);
    ListNode result = this.oddEvenList(l1);
    while (result != null) {
      log.info(JSON.toJSONString(result.val));
      result = result.next;
    }
  }

  /**
   * Write a program to find the node at which the intersection of two singly linked lists begins.
   * 如果两个链表相交于某一节点，那么在这个相交节点之后的所有节点都是两个链表所共有的.
   * 链表相交指的是相交点的内存地址相同不单是值相同.
   */
  public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    if (headA == null || headB == null) {
      return null;
    }
    int aLength = 0;
    int bLength = 0;
    ListNode currA = headA;
    ListNode currB = headB;
    while (currA != null) {
      aLength++;
      currA = currA.next;
    }
    while (currB != null) {
      bLength++;
      currB = currB.next;
    }
    int diff = aLength - bLength;
    if (diff > 0) {
      while (diff > 0) {
        diff--;
        headA = headA.next;
      }
    } else {
      while (diff < 0) {
        diff++;
        headB = headB.next;
      }
    }
    while (headA != null && headB != null) {
      if (headA == headB) {
        return headA;
      }
      headA = headA.next;
      headB = headB.next;
    }
    return null;
  }

  /**
   * Sort a linked list in O(n log n) time using constant space complexity.
   */
  public ListNode sortList(ListNode head) {
    if (head == null || head.next == null) {
      return head;
    }
    ListNode mid = getMiddle(head);
    ListNode halfNode = mid.next;
    mid.next = null;
    return mergeTwoLists(sortList(head), sortList(halfNode));
  }

  /**
   * Given a linked list and a value x, partition it such that all nodes less than x come before
   * nodes greater than or equal to x.
   * You should preserve the original relative order of the nodes in each of the two partitions.
   * Example:
   * Input: head = 1->4->3->2->5->2, x = 3
   * Output: 1->2->2->4->3->5
   */
  public ListNode partition(ListNode head, int x) {
    if (head == null || head.next == null) {
      return head;
    }
    ListNode smallHead = new ListNode(-1);
    ListNode bigHead = new ListNode(-1);
    ListNode small = smallHead;
    ListNode big = bigHead;
    while (head != null) {
      if (head.val < x) {
        small.next = new ListNode(head.val);
        small = small.next;
      } else {
        big.next = new ListNode(head.val);
        big = big.next;
      }
      head = head.next;
    }
    while (small.next != null) {
      small = small.next;
    }
    small.next = bigHead.next;
    return smallHead.next;
  }

  @Test
  public void testPartition() {
    ListNode l = new ListNode(3);
    l.next = new ListNode(1);
    l.next.next = new ListNode(2);
    ListNode result = this.partition(l, 3);
    while (result != null) {
      log.info(JSON.toJSONString(result.val));
      result = result.next;
    }
  }

  /**
   * Remove all elements from a linked list of integers that have value val.
   */
  public ListNode removeElements(ListNode head, int val) {
    if (head == null) {
      return head;
    }
    head.next = removeElements(head.next, val);
    if (head.val == val) {
      return head.next;
    }
    return head;
  }

  /**
   * Reverse a linked list from position m to n. Do it in one-pass.
   */
  public ListNode reverseBetween(ListNode head, int m, int n) {
    if (head == null) {
      return head;
    }
    ListNode dummy = new ListNode(-1);
    dummy.next = head;
    ListNode pre = dummy;
    for (int i = 0; i < m - 1; i++) {
      pre = pre.next;
    }
    ListNode start = pre.next;
    ListNode then = start.next;
    for (int i = 0; i < n - m; i++) {
      start.next = then.next;
      then.next = pre.next;
      pre.next = then;
      then = start.next;
    }
    return dummy.next;
  }

}
