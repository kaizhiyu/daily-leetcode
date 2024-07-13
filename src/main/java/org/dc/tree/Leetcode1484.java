package org.dc.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * class Solution {//官方dfs+hash_map
 * public:
 * unordered_map<Node*, NodeCopy*>map;//建立一个hash_map
 *     NodeCopy* copyRandomBinaryTree(Node* root) {
 *         if(root==nullptr) return nullptr;
 *         if(map.count(root)) return map[root];//哈希图中有对应的直接返回
 *         NodeCopy*TR=new NodeCopy(root->val);//建立一个节点
 *         map[root]=TR;//建立映射关系
 *         TR->left=copyRandomBinaryTree(root->left);//递归建立以下三指针
 *         TR->right=copyRandomBinaryTree(root->right);
 *         TR->random=copyRandomBinaryTree(root->random);
 *         return TR;
 *     }
 * };
 *
 *
 * 参考： https://blog.csdn.net/wutao32111/article/details/109021414
 */
public class Leetcode1484 {
    public static class Node {
        int val;
        Node left;
        Node right;
        Node random;

        public Node() {}

        public Node(int val) {
            this.val = val;
        }
    }

    public Map<Node, Node> save = new HashMap<>();

    public static void main(String[] args) {
        Leetcode1484 leetcode1484 = new Leetcode1484();

        Leetcode1484.Node root = new Leetcode1484.Node(1);
        Leetcode1484.Node root1 = new Leetcode1484.Node(2);
        Leetcode1484.Node root2 = new Leetcode1484.Node(3);
        root.left = root1;
        root.right = root2;
        root.random = root1;
        root1.random = root2;
        root2.random = root;

        leetcode1484.preVisit(root);
        Node newNode = leetcode1484.copyRandomBinaryTree(root);
        leetcode1484.preVisit(newNode);
    }

    public void preVisit(Node root) {
        if (root == null) {
            return;
        }

        System.out.println(root.val + " " + root.random.val);
        preVisit(root.left);
        preVisit(root.right);
    }

    public Node copyRandomBinaryTree(Node root) {
        if (root == null) {
            return root;
        }

        if (save.containsKey(root)) {
            return save.get(root);
        }

        Node tr = new Node(root.val);
        save.put(root, tr);

        tr.left = copyRandomBinaryTree(root.left);
        tr.right = copyRandomBinaryTree(root.right);
        tr.random = copyRandomBinaryTree(root.random);
        return tr;
    }
}
