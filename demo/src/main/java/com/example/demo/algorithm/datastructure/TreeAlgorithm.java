package com.example.demo.algorithm.datastructure;

import com.alibaba.fastjson.JSON;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author ransahojie
 * @Description: TODO
 * @date 2018/9/20 16:32
 */
@Slf4j
public class TreeAlgorithm {

  /**
   * 二叉树最大深度.
   */
  public int maxDepth(TreeNode root) {
    if (root == null) {
      return 0;
    }
    return maxDepth(root.right) > maxDepth(root.left) ? maxDepth(root.right)
        : maxDepth(root.left) + 1;
  }

  /**
   * 二叉树最小深度
   * 每次return要加1 即当前节点.
   */
  public int minDepth(TreeNode root) {
    if (root == null) {
      return 0;
    }

    if (root.left == null) {
      return minDepth(root.right) + 1;
    }
    if (root.right == null) {
      return minDepth(root.left) + 1;
    }

    return minDepth(root.right) > minDepth(root.left) ? minDepth(root.right)
        : minDepth(root.left) + 1;
  }

  /**
   * 验证二叉搜索树.
   */
  public boolean isValidBST2(TreeNode root) {
    return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
  }

  /**
   * 验证二叉搜索树.
   */
  public boolean isValid(TreeNode treeNode, long lowerBound, long upperBound) {
    if (treeNode == null) {
      return true;
    }
    if (treeNode.val >= upperBound || treeNode.val <= lowerBound) {
      return false;
    }
    return isValid(treeNode.left, lowerBound, treeNode.val) && isValid(treeNode.right, treeNode.val,
        upperBound);
  }

  /**
   * 对称二叉树.
   */
  public boolean isSymmetric(TreeNode root) {
    return isMirror(root, root);
  }

  private boolean isMirror(TreeNode left, TreeNode right) {
    if (left == null && right == null) {
      return true;
    }
    if (left == null || right == null) {
      return false;
    }
    return left.val == right.val && isMirror(left.left, right.right) && isMirror(left.right,
        right.left);
  }

  /**
   * 二叉树的层次遍历.
   */
  public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> l = new ArrayList<>();

    if (root == null) {
      return l;
    }

    Deque<TreeNode> q = new ArrayDeque();
    q.offer(root);

    while (!q.isEmpty()) {
      List<Integer> tmp = new ArrayList<>();
      int size = q.size();
      for (int i = 0; i < size; i++) {
        TreeNode top = q.poll();
        tmp.add(top.val);
        if (top.left != null) {
          q.offer(top.left);
        }
        if (top.right != null) {
          q.offer(top.right);
        }
      }

      l.add(tmp);
    }

    return l;
  }

  /**
   * 将有序数组转换为二叉搜索树.
   * 二分法
   */
  public TreeNode sortedArrayToBST(int[] nums) {
    if (nums == null || nums.length == 0) {
      return null;
    }
    return transform(nums, 0, nums.length - 1);
  }


  private TreeNode transform(int[] nums, int l, int r) {
    if (l < r) {
      int mid = (l + r) / 2;
      TreeNode node = new TreeNode(nums[mid]);
      node.left = transform(nums, l, mid);
      node.right = transform(nums, mid, r);
      return node;
    }

    return null;
  }

  /**
   * Given a binary tree, return the inorder traversal of its nodes' values.
   */
  public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    doTraversal(root, result);
    return result;
  }

  private void doTraversal(TreeNode root, List<Integer> list) {
    if (root != null) {
      doTraversal(root.left, list);
      list.add(root.val);
      doTraversal(root.right, list);
    }
  }

  /**
   * Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie,
   * from left to right, then right to left for the next level and alternate between).
   * poll head or tail-->offer left or right
   */
  public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    if (root == null) {
      return new ArrayList<>();
    }
    List<List<Integer>> result = new ArrayList<>();
    Deque<TreeNode> deque = new ArrayDeque<>();
    deque.offer(root);
    boolean flag = true;
    while (!deque.isEmpty()) {
      List<Integer> tmp = new ArrayList<>();
      int size = deque.size();
      for (int i = 0; i < size; i++) {
        TreeNode node = flag ? deque.pollFirst() : deque.pollLast();
        tmp.add(node.val);
        if (flag) {
          if (node.left != null) {
            deque.offer(node.left);
          }
          if (node.right != null) {
            deque.offer(node.right);
          }
        } else {
          if (node.right != null) {
            deque.offer(node.right);
          }
          if (node.left != null) {
            deque.offer(node.left);
          }
        }
      }
      result.add(tmp);
      flag = !flag;
    }
    return result;
  }

  /**
   * Given preorder and inorder traversal of a tree, construct the binary tree.
   * every time from preorder get root node then from inorder get the left and right nodes.
   */
  public TreeNode buildTree(int[] preorder, int[] inorder) {
    if (preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0) {
      return null;
    }
    TreeNode root = new TreeNode(preorder[0]);
    int index = 0;
    while (preorder[0] != inorder[index]) {
      index++;
    }
    if (index != 0) {
      int[] leftInorder = Arrays.copyOfRange(inorder, 0, index);
      root.left = buildTree(Arrays.copyOfRange(preorder, 1, index + 1), leftInorder);
    }
    if (index != inorder.length - 1) {
      int[] rightInorder = Arrays.copyOfRange(inorder, index + 1, inorder.length);
      root.right = buildTree(Arrays.copyOfRange(preorder, index + 1, preorder.length),
          rightInorder);
    }
    return root;
  }

  @Test
  public void testBuildTree() {
    TreeNode node = this.buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});
    List<List<Integer>> result = this.levelOrder(node);
    log.info(JSON.toJSONString(result));
  }

  /**
   * Given a binary tree, flatten it to a linked list in-place.
   */
  public void flatten(TreeNode root) {
    if (root == null) {
      return;
    }
    TreeNode left = root.left;
    TreeNode right = root.right;
    root.left = null;
    flatten(left);
    flatten(right);
    root.right = left;
    TreeNode tmp = root;
    while (tmp.right != null) {
      tmp = tmp.right;
    }
    tmp.right = right;
  }

}
