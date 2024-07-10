/*
    The Trie data structure :
*/

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode(' ');
    }

    public boolean isEmpty() {
        return root.children.isEmpty();
    }

    public void clear() {
        root.children.clear();
    }

    public void insert(String s) {
        if (contains(s))
            ;
        else {
            char[] chars = s.toCharArray();
            TrieNode tmp = root;
            TrieNode ins;
            int counter = 1;
            if (isEmpty()) {
                for (char forCh : chars) {
                    if (counter == s.length()) {
                        tmp.children.add(new TrieNode(forCh, true, new ArrayList<TrieNode>()));
                    } else {
                        ins = new TrieNode(forCh, new ArrayList<TrieNode>());
                        tmp.children.add(ins);
                        tmp = ins;
                    }
                    counter++;
                }
            } else {
                for (char forCh : chars) {
                    Iterator<TrieNode> ite = tmp.children.iterator();
                    boolean exist = false;
                    TrieNode nx;

                    while (ite.hasNext()) {
                        nx = ite.next();
                        if (nx.info == forCh) {
                            tmp = nx;
                            exist = true;
                        }
                    }

                    if (!exist) {
                        ins = new TrieNode(forCh, new ArrayList<TrieNode>());
                        tmp.children.add(ins);
                        tmp = ins;
                    }

                    if (counter == s.length()) {
                        tmp.anEnd = true;
                    }

                    counter++;
                }
            }
        }
    }

    public int size() {
        if (isEmpty())
            return 0;

        Stack<TrieNode> st = new Stack<>();
        TrieNode tmp = root;
        Iterator<TrieNode> ite;
        int count = 0; // including the root

        ite = root.children.iterator();
        while (ite.hasNext()) {
            st.push(ite.next());
        }

        while (!st.isEmpty()) {
            tmp = st.pop();
            ite = tmp.children.iterator();
            while (ite.hasNext()) {
                st.push(ite.next());
            }
            count++;
        }
        return count;
    }

    public boolean isPrefix(String p) throws Exception {
        if (isEmpty())
            throw new Exception("The tree is empty");

        Stack<TrieNode> st = new Stack<>();
        TrieNode tmp;
        Iterator<TrieNode> ite;
        String tmpString = "";

        ite = root.children.iterator();
        while (ite.hasNext()) { // start from the parent childen
            st.push(ite.next());
        }

        while (!st.isEmpty()) {
            tmp = st.pop();
            ite = tmp.children.iterator();
            while (ite.hasNext()) {
                st.push(ite.next());
            }

            tmpString += tmp.info;

            if (tmpString.equals(p))
                return true;

            if (tmp.children.isEmpty())
                tmpString = "";
        }

        return false;
    }

    public boolean contains(String s) {
        Stack<TrieNode> st = new Stack<>();
        TrieNode tmp;
        Iterator<TrieNode> ite;
        String tmpString = "";
        Stack<String> pre = new Stack<>();

        ite = root.children.iterator();
        while (ite.hasNext()) { // start from the parent children
            st.push(ite.next());
        }

        while (!st.isEmpty()) {
            tmp = st.pop();
            ite = tmp.children.iterator();
            while (ite.hasNext()) {
                st.push(ite.next());
            }

            tmpString += tmp.info;
            if (tmp.children.size() > 1) {
                for (int i = 0; i < tmp.children.size(); i++)
                    pre.add(tmpString);
            }

            if (tmp.anEnd) {
                if (tmpString.equals(s))
                    return true;
                if (tmp.children.isEmpty())
                    if (!pre.isEmpty())
                        tmpString = pre.pop();
                    else
                        tmpString = "";
            }
        }

        return false;
    }

    public String[] allWordsPrefix(String p) throws Exception {
        if (isEmpty())
            throw new Exception("The tree is empty");

        Stack<TrieNode> st = new Stack<>();
        TrieNode tmp;
        Iterator<TrieNode> ite;
        String tmpString = "";
        Stack<String> pre = new Stack<>();
        ArrayList<String> tmpArray = new ArrayList<>();

        ite = root.children.iterator();
        while (ite.hasNext()) { // start from the parent children
            st.push(ite.next());
        }

        while (!st.isEmpty()) {
            tmp = st.pop();
            ite = tmp.children.iterator();
            while (ite.hasNext()) {
                st.push(ite.next());
            }

            tmpString += tmp.info;
            if (tmp.children.size() > 1) {
                for (int i = 0; i < tmp.children.size() - 1; i++)
                    pre.add(tmpString);
            }

            if (tmp.anEnd) {
                if (tmpString.startsWith(p))
                    tmpArray.add(tmpString);
                if (tmp.children.isEmpty())
                    if (!pre.isEmpty())
                        tmpString = pre.pop();
                    else
                        tmpString = "";
            }
        }

        String[] result = new String[tmpArray.size()];
        tmpArray.toArray(result);

        return result;
    }

    public void delete(String s) throws Exception {
        if (isEmpty())
            throw new Exception("The tree is empty");
        if (!contains(s))
            throw new Exception("The word does not exist in the Trie");

        Stack<TrieNode> st = new Stack<>();
        Stack<TrieNode> parentsStack = new Stack<>();
        Stack<String> pre = new Stack<>();
        TrieNode son;
        Iterator<TrieNode> ite;
        String tmpString = "";
        boolean found = false;

        ite = root.children.iterator();
        while (ite.hasNext()) { // start from the parent children
            st.push(ite.next());
        }

        while (!st.isEmpty() && !found) {
            son = st.pop();

            ite = son.children.iterator();
            while (ite.hasNext()) {
                st.push(ite.next());
            }

            parentsStack.push(son);
            tmpString += son.info;
            if (son.children.size() > 1) {
                for (int i = 0; i < son.children.size(); i++)
                    pre.push(tmpString);
            }

            if (son.anEnd) {
                if (tmpString.equals(s)) {
                    if (!son.children.isEmpty()) { // we cant remove the node, it has children
                        son.anEnd = false;
                    } else { // here, it does not have children
                        TrieNode parentNode;
                        son.anEnd = false;
                        parentNode = parentsStack.pop();
                        while (!son.anEnd && !(parentsStack.isEmpty())) {
                            parentNode = parentsStack.pop();
                            parentNode.children.remove(son);
                            son = parentNode;
                        }
                        if (!parentNode.anEnd)
                            root.children.remove(parentNode);
                    }
                    found = true;
                }
                if (son.children.isEmpty())
                    if (!pre.isEmpty()) {
                        String preString = tmpString;
                        tmpString = pre.pop();
                        int iterations = preString.length() - tmpString.length();
                        for (int i = 0; i <= iterations; i++)
                            parentsStack.pop();
                    } else {
                        tmpString = "";
                        parentsStack.clear();
                    }
            }
        }
    }

    public void printTrie() throws Exception {
        if (isEmpty())
            throw new Exception("The tree is empty");

        Stack<TrieNode> st = new Stack<>();
        TrieNode tmp = root;
        Iterator<TrieNode> ite;
        String tmpString = "";
        Stack<String> pre = new Stack<>();

        ite = root.children.iterator();
        while (ite.hasNext()) {
            st.push(ite.next());
        }

        while (!st.isEmpty()) {
            tmp = st.pop();
            ite = tmp.children.iterator();
            while (ite.hasNext()) {
                st.push(ite.next());
            }

            tmpString += tmp.info;

            if (tmp.children.size() > 1) {
                for (int i = 0; i < tmp.children.size() - 1; i++)
                    pre.add(tmpString);
            }

            if (tmp.anEnd) {
                System.out.print(tmpString + " ");
                if (tmp.children.isEmpty())
                    if (!pre.isEmpty())
                        tmpString = pre.pop();
                    else
                        tmpString = "";
            }
        }
        System.out.println();
    }
}
