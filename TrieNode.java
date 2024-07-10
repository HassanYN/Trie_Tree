/*
    The trie node class
    Using an ArrayList to save the children
    Each node has only one char that called info
*/


import java.util.ArrayList;

public class TrieNode {
    protected ArrayList<TrieNode> children;
    protected boolean anEnd;
    protected char info;

    public TrieNode(Character info, boolean anEnd, ArrayList<TrieNode> children) {
        this.children = children;
        this.anEnd = anEnd;
        this.info = info;
    }

    public TrieNode(Character info, ArrayList<TrieNode> children) {
        this(info, false, children);
    }

    public TrieNode(Character info) {
        this(info, false, new ArrayList<>());
    }
}