package com.example.demo.algorithm.datastructure;

import org.junit.Assert;
import org.junit.Test;
import org.python.bouncycastle.cms.RecipientInfoGenerator;

import java.util.*;

/**
 * @author ransahojie
 * @Description: TODO
 * @date 2018/9/20 16:32
 */
public class TreeAlgorithm {

    /**
     * 二叉树最大深度
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return maxDepth(root.right) > maxDepth(root.left) ? maxDepth(root.right) : maxDepth(root.left) + 1;
    }

    /**
     * 二叉树最小深度
     * 每次return要加1 即当前节点
     *
     * @param root
     * @return
     */
    public int minDepth(TreeNode root) {
        if (root == null) return 0;

        if (root.left == null) return minDepth(root.right) + 1;
        if (root.right == null) return minDepth(root.left) + 1;

        return minDepth(root.right) > minDepth(root.left) ? minDepth(root.right) : minDepth(root.left) + 1;
    }

    /**
     * 验证二叉搜索树
     *
     * @param root
     * @return
     */
    public boolean isValidBST2(TreeNode root) {
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    /**
     * 验证二叉搜索树
     *
     * @param treeNode
     * @param lowerBound
     * @param upperBound
     * @return
     */
    public boolean isValid(TreeNode treeNode, long lowerBound, long upperBound) {
        if (treeNode == null) return true;
        if (treeNode.val >= upperBound || treeNode.val <= lowerBound) return false;
        return isValid(treeNode.left, lowerBound, treeNode.val) && isValid(treeNode.right, treeNode.val, upperBound);
    }

    /**
     * 对称二叉树
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        return isMirror(root, root);
    }

    private boolean isMirror(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        return left.val == right.val && isMirror(left.left, right.right) && isMirror(left.right, right.left);
    }

    /**
     * 二叉树的层次遍历
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> l = new ArrayList<>();

        if (root == null) return l;

        Deque<TreeNode> q = new ArrayDeque();
        q.offer(root);

        while (!q.isEmpty()) {
            List<Integer> tmp = new ArrayList<>();
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode top = q.poll();
                tmp.add(top.val);
                if (top.left != null) q.offer(top.left);
                if (top.right != null) q.offer(top.right);
            }

            l.add(tmp);
        }

        return l;
    }

    /**
     * 将有序数组转换为二叉搜索树
     * 二分法
     *
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) return null;
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
     * 合并两个有序数组
     * 从后往前合并到一个数组
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
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
     * 第一个错误的版本
     * @param n
     * @return
     */
    public int firstBadVersion(int n) {
        int left = 1, right = n;
        while(left < right) {
            int mid = left + (right - left) / 2;
            if(isBadVersion(mid)) right = mid;
            else left = mid + 1;
        }
        return right;
    }

    /**
     * 判断是否错误
     * @param mid
     * @return
     */
    private boolean isBadVersion(int mid) {
        return true;
    }
}
