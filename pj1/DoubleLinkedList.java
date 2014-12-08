import java.util.Iterator;

public class DoubleLinkedList<T> implements Iterable<T> {
	
	DoubleLinkedListNode<T> head;
	DoubleLinkedListNode<T> tail;
	DoubleLinkedListNode<T> curr;
	
	public int length = 0;
	
	public DoubleLinkedList(){
		head = tail = curr = null;
	}
	
	public DoubleLinkedList(T[] values){
		head = tail = curr = null;
		for(T value : values){
			this.addTail(value);
		}
	}
	
	public void addHead(T value){
		DoubleLinkedListNode<T> node = new DoubleLinkedListNode<T>(value);
		if(head == null){
			head = tail = node;
		}else{
			node.next = head;
			head.prev = node;
			head = node;
		}
		length++;
	}
	
	public void addTail(T value){
		DoubleLinkedListNode<T> node = new DoubleLinkedListNode<T>(value);
		if(tail == null){
			head = tail = node;
		}else{
			node.prev = tail;
			tail.next = node;
			tail = node;
		}
		length++;
	}
	
	public void insertAt(int index, T value){
		if(index < 0 || index > length - 1)
			throw new IndexOutOfBoundsException(index + "");
		
		DoubleLinkedListNode<T> node = head;
		while(index-- > 0){
			node = node.next;
		}
		DoubleLinkedListNode<T> newNode = new DoubleLinkedListNode<T>(value);
		if(node == head){
			head.prev = newNode;
			newNode.next = head;
			head = newNode;
		} else if(node == tail){
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		} else {
			node.prev.next = newNode;
			newNode.prev = node.prev;
			
			node.prev = newNode;
			newNode.next = node;
		}
		length++;
	}
	
	public void removeAt(int index){
		if(index < 0 || index > length - 1)
			throw new IndexOutOfBoundsException(index + "");
		
		DoubleLinkedListNode<T> node = this.head;
		while(index-- > 0){
			node = node.next;
		}
		if(node == head){
			head = head.next;
			head.prev = null;
		} else if(node == tail){
			tail = tail.prev;
			tail.next = null;
		} else {
			node.prev.next = node.next;
			node.next = node.prev;
		}
	}
	
	public T firstElement(){
		if(this.head == null)
			throw new IndexOutOfBoundsException("list is empty");
		return this.head.value;
	}
	
	public T lastElement(){
		if(this.tail == null)
			throw new IndexOutOfBoundsException("list is empty");
		return this.tail.value;
	}
	
	public T elementAt(int index){
		if(index < 0 || index > length - 1)
			throw new IndexOutOfBoundsException(index + "");
		
		DoubleLinkedListNode<T> node = head;
		while(index-- > 0)
			node = node.next;
		return node.value;
	}
	
	public int findElement(T value){
		DoubleLinkedListNode<T> node = head;
		for(int i=0; node != null; i++){
			if(value.equals(node.value))
				return i;
			node = node.next;
		}
		return -1;
	}
	
	public Iterator<T> iterator(){
		return new DoubleLinkedListIterator<T>(head);
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder("[");
		DoubleLinkedListNode<T> node = head;
		while(node != null){
			builder.append(node.value.toString() + ",");
			node = node.next;
		}
		builder.setCharAt(builder.length()-1, ']');
		return builder.toString();
	}
	
	public static void main(String[] args){
		Integer[] values = { 1, 2, 3};
		DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>(values);
		System.out.println(list.toString());
		list.addHead(0);
		System.out.println(list.toString());
		list.addTail(4);
		System.out.println(list.toString());
		list.insertAt(1, 5);
		System.out.println(list.toString());
		list.removeAt(1);
		System.out.println(list.toString());
	}
}

class DoubleLinkedListNode<T> {
	
	DoubleLinkedListNode<T> prev;
	DoubleLinkedListNode<T> next;
	T value;
	
	DoubleLinkedListNode(){
		prev = next = null;
	}
	
	DoubleLinkedListNode(T value){
		this.value = value;
		prev = next = null;
	}
}

class DoubleLinkedListIterator<T> implements Iterator<T>{

	private DoubleLinkedListNode<T> curr;
	
	DoubleLinkedListIterator(DoubleLinkedListNode<T> curr){
		this.curr = curr;
	}
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		if(curr == null)
			return false;
		return this.curr.next != null;
	}

	@Override
	public T next() {
		// TODO Auto-generated method stub
		return this.curr.value;
	}
}