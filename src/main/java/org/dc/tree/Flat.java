package org.dc.tree;

import java.util.ArrayList;
import java.util.List;

public class Flat {

    public static void main(String[] args) {
        // [1,2,5,3,4,null,6]
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node5 = new TreeNode(5);
        root.left = node1;
        root.right = node5;
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        node1.left = node3;
        node1.right = node4;
        TreeNode node6 = new TreeNode(6);
        node5.right = node6;

        Flat flat = new Flat();
        flat.flatten(root);
    }

    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }

        List<Integer> flat = new ArrayList<>();
        flatten(root, flat);

        TreeNode dummy = new TreeNode(0, null, null);
        TreeNode cur = dummy;
        for (Integer val : flat) {
            TreeNode node = new TreeNode(val, null, null);
            cur.right = node;
            cur = node;
        }
        root = dummy.right;
    }

    public void flatten(TreeNode root, List<Integer> flat) {
        if (root == null) {
            return;
        }

        flat.add(root.val);

        flatten(root.left, flat);
        flatten(root.right, flat);
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
