package list;

import junit.framework.*;


public class TestClass extends TestCase {
	public TestClass(String name) {
		super(name);
	}
	
	public void newList(){
		DList list = new DList();
		assertTrue(list.isEmpty());
		assertTrue(list.front() == null);
		assertTrue(list.back() == null);
	}
	
	public void testInsertFrontToEmptyList(){
		DList list = new DList();
		
		Integer one = Integer.valueOf(1);
		list.insertFront(one);
		
		assertTrue(list.length() == 1);
		assertTrue(this.isValid(list));
		assertTrue(list.front().item.equals(one));
		assertTrue(list.back().item.equals(one));
	}
	
	public void testInsertFrontToNotEmptyList(){
		DList list = new DList();
		
		Integer one = Integer.valueOf(1);
		Integer two = Integer.valueOf(2);
		
		list.insertFront(one);
		list.insertFront(two);
		
		assertTrue(list.length() == 2);
		assertTrue(this.isValid(list));
		assertTrue(list.front().item.equals(two));
		assertTrue(list.back().item.equals(one));
	}
	
	public void testInsertBackToEmptyList(){
		DList list = new DList();
		
		Integer one = Integer.valueOf(1);
		list.insertBack(one);
		
		assertTrue(list.length() == 1);
		assertTrue(this.isValid(list));
		assertTrue(list.front().item.equals(one));
		assertTrue(list.back().item.equals(one));
	}
	
	public void testInsertBackToNotEmptyList(){
		DList list = new DList();
		
		Integer one = Integer.valueOf(1);
		Integer two = Integer.valueOf(2);
		
		list.insertBack(one);
		list.insertBack(two);
		
		assertTrue(list.length() == 2);
		assertTrue(this.isValid(list));
		assertTrue(list.front().item.equals(one));
		assertTrue(list.back().item.equals(two));
	}
	
	public void testInsertAfterFisrtNode(){
		DList list = new DList();
		
		Integer one = Integer.valueOf(1);
		list.insertFront(one);
		
		DListNode nodeOne = list.front();
		Integer two = Integer.valueOf(2);
		
		list.insertAfter(two, nodeOne);
		assertTrue(list.length() == 2);
		assertTrue(this.isValid(list));
		assertTrue(list.front().item.equals(one));
		assertTrue(list.back().item.equals(two));
	}
	
	public void testInsertAfterOtherNodes(){
		DList list = new DList();
		
		Integer one = Integer.valueOf(1);
		list.insertBack(one);
		
		Integer two = Integer.valueOf(2);
		list.insertBack(two);
		
		DListNode nodetwo = list.back();
		Integer three = Integer.valueOf(3);
		list.insertAfter(three, nodetwo);
		
		assertTrue(list.length() == 3);
		assertTrue(this.isValid(list));
		assertTrue(list.front().item.equals(one));
		assertTrue(list.back().item.equals(three));
	}

	public void testInsertBeforeLastNode(){
		DList list = new DList();
		
		Integer one = Integer.valueOf(1);
		list.insertBack(one);
		
		DListNode nodeOne = list.back();
		Integer two = Integer.valueOf(2);
		
		list.insertBefore(two, nodeOne);
		assertTrue(list.length() == 2);
		assertTrue(this.isValid(list));
		assertTrue(list.front().item.equals(two));
		assertTrue(list.back().item.equals(one));
	}
	
	public void testInsertBeforeOtherNodes(){
		DList list = new DList();
		
		Integer one = Integer.valueOf(1);
		list.insertBack(one);
		
		Integer two = Integer.valueOf(2);
		list.insertBack(two);
		
		DListNode nodetwo = list.back();
		Integer three = Integer.valueOf(2);
		list.insertAfter(three, nodetwo);
		
		assertTrue(list.length() == 3);
		assertTrue(this.isValid(list));
		assertTrue(list.front().item.equals(one));
		assertTrue(list.back().item.equals(three));
	}
	
	public void testRemoveNodeFromListWithOneNode(){
		DList list = new DList();
		
		Integer one = Integer.valueOf(1);
		list.insertBack(one);
		
		DListNode nodeOne = list.front();
		list.remove(nodeOne);
		
		assertTrue(list.isEmpty());
		assertTrue(this.isValid(list));
	}
	
	public void testRemoveNodeFromListWithNodes(){
		DList list = new DList();
		
		Integer one = Integer.valueOf(1);
		list.insertBack(one);
		
		Integer two = Integer.valueOf(2);
		list.insertBack(two);
		
		DListNode nodetwo = list.back();
		list.remove(nodetwo);
		
		assertTrue(list.front().item.equals(one));
		assertTrue(list.length() == 1);
		assertTrue(this.isValid(list));
	}
	
	private boolean isValid(DList list){
		int count = 0;
		for(DListNode node = list.front(); node != null; node = list.next(node)){
			if(node.next == null || node.prev == null)
				return false;
			count++;
		}
		return count == list.length();
	}
	
	public static Test suite() {
		return new TestSuite(TestClass.class);
	}
	
	public static void main(String args[]) {
		junit.textui.TestRunner.run(suite());
	}
}
