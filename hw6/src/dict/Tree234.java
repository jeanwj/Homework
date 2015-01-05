/* Tree234.java */

package dict;

/**
 * A Tree234 implements an ordered integer dictionary ADT using a 2-3-4 tree.
 * Only int keys are stored; no object is associated with each key. Duplicate
 * keys are not stored in the tree.
 * 
 * @author Jonathan Shewchuk
 **/
public class Tree234 extends IntDictionary {

	/**
	 * You may add fields if you wish, but don't change anything that would
	 * prevent toString() or find() from working correctly.
	 * 
	 * (inherited) size is the number of keys in the dictionary. root is the
	 * root of the 2-3-4 tree.
	 **/
	Tree234Node root;

	/**
	 * Tree234() constructs an empty 2-3-4 tree.
	 * 
	 * You may change this constructor, but you may not change the fact that an
	 * empty Tree234 contains no nodes.
	 */
	public Tree234() {
		root = null;
		size = 0;
	}

	/**
	 * toString() prints this Tree234 as a String. Each node is printed in the
	 * form such as (for a 3-key node)
	 * 
	 * (child1)key1(child2)key2(child3)key3(child4)
	 * 
	 * where each child is a recursive call to toString, and null children are
	 * printed as a space with no parentheses. Here's an example. ((1)7(11
	 * 16)22(23)28(37 49))50((60)84(86 95 100))
	 * 
	 * DO NOT CHANGE THIS METHOD. The test code depends on it.
	 * 
	 * @return a String representation of the 2-3-4 tree.
	 **/
	public String toString() {
		if (root == null) {
			return "";
		} else {
			/* Most of the work is done by Tree234Node.toString(). */
			return root.toString();
		}
	}

	/**
	 * printTree() prints this Tree234 as a tree, albeit sideways.
	 * 
	 * You're welcome to change this method if you like. It won't be tested.
	 **/
	public void printTree() {
		if (root != null) {
			/* Most of the work is done by Tree234Node.printSubtree(). */
			root.printSubtree(0);
		}
	}

	/**
	 * find() prints true if "key" is in this 2-3-4 tree; false otherwise.
	 * 
	 * @param key
	 *            is the key sought.
	 * @return true if "key" is in the tree; false otherwise.
	 **/
	public boolean find(int key) {
		Tree234Node node = root;
		while (node != null) {
			if (key < node.key1) {
				node = node.child1;
			} else if (key == node.key1) {
				return true;
			} else if ((node.keys == 1) || (key < node.key2)) {
				node = node.child2;
			} else if (key == node.key2) {
				return true;
			} else if ((node.keys == 2) || (key < node.key3)) {
				node = node.child3;
			} else if (key == node.key3) {
				return true;
			} else {
				node = node.child4;
			}
		}
		return false;
	}

	/**
	 * insert() inserts the key "key" into this 2-3-4 tree. If "key" is already
	 * present, a duplicate copy is NOT inserted.
	 * 
	 * @param key
	 *            is the key sought.
	 **/
	public void insert(int key) {
		// Fill in your solution here.
		if (root == null) {
			root = new Tree234Node(null, key);
			size++;
		}
		else
		{
			Tree234Node node = root;
            while (true)
            {
                if (node.isFull())
                {
                    splitNode(node);
                    if(node != root)
                        node = node.parent;
                    node = getAvailableChild(node, key);
                }
                else if (node.isLeaf())
                    break;
                else
                    node = getAvailableChild(node, key);
            }
            
            switch(node.keys)
    		{
    		case 1: 
    			node.keys++;
				if(key < node.key1){
					node.key2 = node.key1;
					node.key1 = key;
				} else{
					node.key2 = key;
				}
				break;
			case 2: 
				node.keys++;
				if(key < node.key1) {
					node.key3 = node.key2;
					node.key2 = node.key1;
					node.key1 = key;
				} else if(key < node.key2){
					node.key3 = node.key2;
					node.key2 = key;
				} else {
					node.key3 = key;
				}
				break;
    			default : throw new java.lang.IllegalStateException("�����ֵ");
    		}
		}
	}

	private Tree234Node getAvailableChild(Tree234Node node, int key){
		switch(node.keys)
		{
			case 1: 
				if(key < node.key1) 
					return node.child1;
				else
					return node.child2;
			case 2:
				if(key < node.key1) 
					return node.child1;
				else if(key < node.key2)
					return node.child2;
				else
					return node.child3;
			case 3:
				if(key < node.key1) 
					return node.child1;
				else if(key < node.key2)
					return node.child2;
				else if(key < node.key3)
					return node.child3;
				else
					return node.child4;
			default : throw new java.lang.IllegalStateException("�����ֵ");
		}
	}

    private void splitNode(Tree234Node node)
    {
        if(node == root)
        	splitRootNode(node);
        else
        	splitOtherNode(node);
    }
	
    private void splitRootNode(Tree234Node node){
    	Tree234Node newRoot = new Tree234Node(null, root.key2);
    	newRoot.child2 = new Tree234Node(newRoot, root.key3);
    	newRoot.child2.child1 = root.child3;
    	newRoot.child2.child2 = root.child4;
    	if(root.child3 != null)
    		root.child3.parent = newRoot.child2;
    	if(root.child4 != null)
    		root.child4.parent = newRoot.child2;
    	
    	newRoot.child1 = root;
    	newRoot.child1.parent = newRoot;
    	newRoot.child1.child3 = null;
    	newRoot.child1.child4 = null;
    	newRoot.child1.keys = 1;
    	root = newRoot;
    }
    
    private void splitOtherNode(Tree234Node node){
    	Tree234Node newNode = new Tree234Node(node.parent, node.key3);
    	newNode.child1 = node.child3;
    	newNode.child2 = node.child4;
    	
    	if(newNode.child1 != null)
    		newNode.child1.parent = newNode;
    	if(newNode.child2 != null)
    		newNode.child2.parent = newNode;
    	
    	node.keys = 1;
    	node.child3 = node.child4 = null;
    	
    	int key2 = node.key2;
    	
    	if(node.parent.keys == 1){
    		if(key2 < node.parent.key1){
    			node.parent.key2 = node.parent.key1;
        		node.parent.key1 = key2;
        		
        		node.parent.child3 = node.parent.child2;
        		node.parent.child2 = newNode;
    		} else {
    			node.parent.key2 = key2;
    			node.parent.child3 = newNode;
    		}
    	} else if(node.parent.keys == 2){
    		if(key2 < node.parent.key1){
        		node.parent.key3 = node.parent.key2;
        		node.parent.key2 = node.parent.key1;
        		node.parent.key1 = key2;
        		
        		node.parent.child4 = node.parent.child3;
        		node.parent.child3 = node.parent.child2;
        		node.parent.child2 = newNode;
        	} else if(key2 < node.parent.key2){
        		node.parent.key3 = node.parent.key2;
        		node.parent.key2 = key2;
        		
        		node.parent.child4 = node.parent.child3;
        		node.parent.child3 = newNode;
        	} else {
        		node.parent.key3 = key2;
        		node.parent.child4 = newNode;
        	} 
    	}else{
    		throw new java.lang.IllegalStateException("�����ֵ");
    	}
    	node.parent.keys++;
    }
    
	/**
	 * testHelper() prints the String representation of this tree, then compares
	 * it with the expected String, and prints an error message if the two are
	 * not equal.
	 * 
	 * @param correctString
	 *            is what the tree should look like.
	 **/
	public void testHelper(String correctString) {
		String treeString = toString();
		System.out.println(treeString);
		if (!treeString.equals(correctString)) {
			System.out.println("ERROR:  Should be " + correctString);
		}
	}

	/**
	 * main() is a bunch of test code. Feel free to add test code of your own;
	 * this code won't be tested or graded.
	 **/
	public static void main(String[] args) {
		Tree234 t = new Tree234();

		System.out.println("\nInserting 84.");
		t.insert(84);
		t.testHelper("84");

		System.out.println("\nInserting 7.");
		t.insert(7);
		t.testHelper("7 84");

		System.out.println("\nInserting 22.");
		t.insert(22);
		t.testHelper("7 22 84");

		System.out.println("\nInserting 95.");
		t.insert(95);
		t.testHelper("(7)22(84 95)");

		System.out.println("\nInserting 50.");
		t.insert(50);
		t.testHelper("(7)22(50 84 95)");

		System.out.println("\nInserting 11.");
		t.insert(11);
		t.testHelper("(7 11)22(50 84 95)");

		System.out.println("\nInserting 37.");
		t.insert(37);
		t.testHelper("(7 11)22(37 50)84(95)");

		System.out.println("\nInserting 60.");
		t.insert(60);
		t.testHelper("(7 11)22(37 50 60)84(95)");

		System.out.println("\nInserting 1.");
		t.insert(1);
		t.testHelper("(1 7 11)22(37 50 60)84(95)");

		System.out.println("\nInserting 23.");
		t.insert(23);
		t.testHelper("(1 7 11)22(23 37)50(60)84(95)");

		System.out.println("\nInserting 16.");
		t.insert(16);
		t.testHelper("((1)7(11 16)22(23 37))50((60)84(95))");

		System.out.println("\nInserting 100.");
		t.insert(100);
		t.testHelper("((1)7(11 16)22(23 37))50((60)84(95 100))");

		System.out.println("\nInserting 28.");
		t.insert(28);
		t.testHelper("((1)7(11 16)22(23 28 37))50((60)84(95 100))");

		System.out.println("\nInserting 86.");
		t.insert(86);
		t.testHelper("((1)7(11 16)22(23 28 37))50((60)84(86 95 100))");

		System.out.println("\nInserting 49.");
		t.insert(49);
		t.testHelper("((1)7(11 16)22(23)28(37 49))50((60)84(86 95 100))");

		System.out.println("\nInserting 81.");
		t.insert(81);
		t.testHelper("((1)7(11 16)22(23)28(37 49))50((60 81)84(86 95 100))");

		System.out.println("\nInserting 51.");
		t.insert(51);
		t.testHelper("((1)7(11 16)22(23)28(37 49))50((51 60 81)84(86 95 100))");

		System.out.println("\nInserting 99.");
		t.insert(99);
		t.testHelper("((1)7(11 16)22(23)28(37 49))50((51 60 81)84(86)95(99 100))");

		System.out.println("\nInserting 75.");
		t.insert(75);
		t.testHelper("((1)7(11 16)22(23)28(37 49))50((51)60(75 81)84(86)95"
				+ "(99 100))");

		System.out.println("\nInserting 66.");
		t.insert(66);
		t.testHelper("((1)7(11 16)22(23)28(37 49))50((51)60(66 75 81))84((86)95"
				+ "(99 100))");

		System.out.println("\nInserting 4.");
		t.insert(4);
		t.testHelper("((1 4)7(11 16))22((23)28(37 49))50((51)60(66 75 81))84"
				+ "((86)95(99 100))");

		System.out.println("\nInserting 80.");
		t.insert(80);
		t.testHelper("(((1 4)7(11 16))22((23)28(37 49)))50(((51)60(66)75"
				+ "(80 81))84((86)95(99 100)))");

		System.out.println("\nFinal tree:");
		t.printTree();
	}

}
