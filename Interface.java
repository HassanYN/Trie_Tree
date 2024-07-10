/* 
   To start the program, run this class
*/
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Thread;

public class Interface {
    static BST<String> dic; // Using BST to save the dictionary (for fast searching)
    static Trie tree;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("dictionary.txt"));
        dic = new BST<>();

        System.out.println("Reading the given dictionary ...");
        System.out.println("...");
        while (sc.hasNext()) {
            dic.insert(sc.nextLine());
        }
        System.out.println("Reading Completed, to the main page ...");
        sc.close();
        choices();
    }

    public static void choices() throws InterruptedException {
        char[] chars;
        boolean done = false;
        System.out.println("\n\n\t\tTrie Project.");
        System.out.println("\t\t1- Create an empty trie.");
        System.out.println("\t\t2- Create a trie with initial letters.");
        System.out.println("\t\t3- Insert a word.");
        System.out.println("\t\t4- Delete a word.");
        System.out.println("\t\t5- List all words that begin with a prefix");
        System.out.println("\t\t6- List all the words in the trie");
        System.out.println("\t\t7- Size of the trie");
        System.out.println("\t\t8- End.");
        System.out.print("Enter Your Choice: ");

        do {
            chars = sc.nextLine().trim().toCharArray();
            if (chars.length == 1 && (chars[0] > 48 && chars[0] < 57))
                done = true;
            else
                System.out.print("Wrong Input, One Number is Needed. Try Again: ");
        } while (!done);

        System.out.print("\n\n");
        switch (chars[0]) {
            case 49: // 1
                Thread.sleep(3000);
                creatEmptyTrie();
                break;
            case 50: // 2 
                Thread.sleep(3000);
                creatTrie();
                break;
            case 51: // 3 
                Thread.sleep(3000);
                insertAWord();
                break;
            case 52: // 4 
                Thread.sleep(3000);
                deleteAWord();
                break;
            case 53: // 5
                Thread.sleep(3000);
                listPrefix();
                break;
            case 54: // 6
                Thread.sleep(3000);
                printTree();
                break;
            case 55: // 7
                Thread.sleep(3000);
                sizeOfTire();
                break;
            case 56: // 8
                System.out.println("Terminating the program ...");
                Thread.sleep(3000);
                sc.close();
                System.exit(1);
                break;
        }
    }

    public static void creatEmptyTrie() throws InterruptedException {
        if (tree != null)
            System.out.println("Comment: A tree already exist");
        else {
            tree = new Trie();
            System.out.println("Comment: The tree has been created");
        }
        System.out.println("Returning to the main page ...");
        Thread.sleep(3000);
        choices();
    }

    public static void creatTrie() throws InterruptedException {
        if (tree != null)
            System.out.println("Comment: A tree already exist");
        else {
            tree = new Trie();

            String str;
            String solu = "";
            ArrayList<String> list = new ArrayList<>();

            System.out.print("Enter your list of letters>  ");
            str = sc.nextLine().trim().replace(" ", "");

            ArrayList<String> wordMix = mixingLetters(str, solu, list);
            ArrayList<String> allMix = new ArrayList<>();

            for (String word : wordMix) {
                for (int i = 0; i < word.length(); i++) {
                    allMix.add(word.substring(0, word.length() - i));
                }
            }

            for (String wo : allMix) { // inserting all the correct combinations in the Trie tree
                if (dic.isInTree(wo) && (wo.length() <= str.length())) {
                    tree.insert(wo);
                }
            }
            printTree();
        }
        System.out.println("Returning to the main page ...");
        Thread.sleep(3000);
        choices();
    }

    public static void insertAWord() throws InterruptedException {
        System.out.print("Enter the Word> ");
        String str = sc.nextLine().trim().replace(" ", "").toUpperCase();
        try {
            if (tree.contains(str)) {
                System.out.println("Comment: this word already in the tree");
            } else if (dic.isInTree(str)) {
                tree.insert(str);
                System.out.println("Comment: " + str + " has been added");
            } else
                System.out.println("WARNING: The input is not in the given dictionary");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("Returning to the main page ...");
        Thread.sleep(3000);
        choices();
    }

    public static void deleteAWord() throws InterruptedException {
        System.out.print("Enter the Word> ");
        String str = sc.nextLine().trim().replace(" ", "");
        try {
            if (!tree.contains(str)) {
                System.out.println("Entered word is not in the tree");
                System.out.println("Returning to the main page ...");
                Thread.sleep(3000);
                choices();
            } else {
                tree.delete(str);
                System.out.println("Deleted successfully");
                System.out.println("Returning to the main page ...");
                Thread.sleep(3000);
                choices();
            }
        } catch (NullPointerException ex) {
            System.out.println("WARNING: Create a tree fisrt");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Returning to the main page ...");
        Thread.sleep(3000);
        choices();
    }

    public static void listPrefix() throws InterruptedException {
        System.out.print("Enter the Prefix> ");
        String str = sc.nextLine().trim().replace(" ", "").toUpperCase();
        String[] result;
        try {
            result = tree.allWordsPrefix(str);
            if (result.length == 0)
                System.out.println("WARNING: There is not a sigle word that starts with this prefix");
            else {
                for (String word : result)
                    System.out.print(word + " ");
                System.out.println();
            }
        } catch (NullPointerException ex) {
            System.out.println("WARNING: Create a tree fisrt");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Returning to the main page ...");
        Thread.sleep(5000);
        choices();
    }

    public static void printTree() throws InterruptedException {
        if (tree != null && !tree.isEmpty())
            System.out.println("The words in the tire are >> ");
        try {
            tree.printTrie();
        } catch (NullPointerException ex) {
            System.out.println("WARNING: Create a tree fisrt");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Returning to the main page ...");
        Thread.sleep(5000);
        choices();
    }

    public static void sizeOfTire() throws InterruptedException {
        try {
            System.out.println("The size of the Tire is> " + tree.size());
        } catch (NullPointerException ex) {
            System.out.println("WARNING: Create a tree fisrt");
        }
        System.out.println("Returning to the main page ...");
        Thread.sleep(5000);
        choices();
    }

    // helper method
    private static ArrayList<String> mixingLetters(String str, String solu, ArrayList<String> result) {
        if (str.length() == 0) {
            result.add(solu);
        } else {
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                String LSubstr = str.substring(0, i);
                String RSubstr = str.substring(i + 1);
                String sum = LSubstr + RSubstr;
                mixingLetters(sum, solu + ch, result);
            }
        }
        return result;
    }
}