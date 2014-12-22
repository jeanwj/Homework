/* HashTableChained.java */

package dict;

import list.*;

/**
 * HashTableChained implements a Dictionary as a hash table with chaining. All
 * objects used as keys must have a valid hashCode() method, which is used to
 * determine which bucket of the hash table an entry is stored in. Each object's
 * hashCode() is presumed to return an int between Integer.MIN_VALUE and
 * Integer.MAX_VALUE. The HashTableChained class implements only the compression
 * function, which maps the hash code to a bucket in the table's range.
 * 
 * DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

	/**
	 * Place any data fields here.
	 **/
	
	private int size, capacity;
	private DList[] entries;

	private static boolean isPrime(int num) {
		if (num == 2 || num == 3)
			return true;
		for (int i = 2; i <= Math.sqrt(num); i++) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}

	private static int minPrime(int num) {
		if (num <= 2)
			return 2;
		for (int i = num; i < Integer.MAX_VALUE; i++) {
			if (isPrime(i))
				return i;
		}
		throw new UnsupportedOperationException();
	}

	/**
	 * Construct a new empty hash table intended to hold roughly sizeEstimate
	 * entries. (The precise number of buckets is up to you, but we recommend
	 * you use a prime number, and shoot for a load factor between 0.5 and 1.)
	 **/

	public HashTableChained(int sizeEstimate) {
		// Your solution here.
		this.capacity = minPrime((int)Math.ceil(sizeEstimate * 4 / 3));
		this.entries = new DList[this.capacity];
	}

	/**
	 * Construct a new empty hash table with a default size. Say, a prime in the
	 * neighborhood of 100.
	 **/

	public HashTableChained() {
		// Your solution here.
		this.entries = new DList[this.capacity = 101];
	}

	/**
	 * Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
	 * to a value in the range 0...(size of hash table) - 1.
	 * 
	 * This function should have package protection (so we can test it), and
	 * should be used by insert, find, and remove.
	 **/

	int compFunction(int code) {
		// Replace the following line with your solution.
		int hashCode = code % this.capacity;
		return hashCode > 0 ? hashCode : - hashCode;
	}

	/**
	 * Returns the number of entries stored in the dictionary. Entries with the
	 * same key (or even the same key and value) each still count as a separate
	 * entry.
	 * 
	 * @return number of entries in the dictionary.
	 **/

	public int size() {
		// Replace the following line with your solution.
		return this.size;
	}

	/**
	 * Tests if the dictionary is empty.
	 * 
	 * @return true if the dictionary has no entries; false otherwise.
	 **/

	public boolean isEmpty() {
		// Replace the following line with your solution.
		return this.size == 0;
	}

	/**
	 * Create a new Entry object referencing the input key and associated value,
	 * and insert the entry into the dictionary. Return a reference to the new
	 * entry. Multiple entries with the same key (or even the same key and
	 * value) can coexist in the dictionary.
	 * 
	 * This method should run in O(1) time if the number of collisions is small.
	 * 
	 * @param key
	 *            the key by which the entry can be retrieved.
	 * @param value
	 *            an arbitrary object.
	 * @return an entry containing the key and value.
	 **/

	public Entry insert(Object key, Object value) {
		// Replace the following line with your solution.
		Entry newEntry = new Entry();
		newEntry.key = key;
		newEntry.value = value;
		
		int index = this.compFunction(key.hashCode());
		DList entry = this.entries[index];
		if(entry == null)
			this.entries[index] = new DList();
		this.entries[index].insertBack(newEntry);
		return newEntry;
	}

	/**
	 * Search for an entry with the specified key. If such an entry is found,
	 * return it; otherwise return null. If several entries have the specified
	 * key, choose one arbitrarily and return it.
	 * 
	 * This method should run in O(1) time if the number of collisions is small.
	 * 
	 * @param key
	 *            the search key.
	 * @return an entry containing the key and an associated value, or null if
	 *         no entry contains the specified key.
	 **/

	public Entry find(Object key) {
		// Replace the following line with your solution.
		int index = this.compFunction(key.hashCode());
		DList entryList = this.entries[index];
		if(entryList == null)
			return null;
		for(DListNode node=entryList.front(); node != null; node = entryList.next(node)){
			Entry entry = (Entry)node.item;
			if(entry.key.hashCode() == key.hashCode() || entry.key.equals(key)){
				return entry;
			}
		}
		return null;
	}

	/**
	 * Remove an entry with the specified key. If such an entry is found, remove
	 * it from the table and return it; otherwise return null. If several
	 * entries have the specified key, choose one arbitrarily, then remove and
	 * return it.
	 * 
	 * This method should run in O(1) time if the number of collisions is small.
	 * 
	 * @param key
	 *            the search key.
	 * @return an entry containing the key and an associated value, or null if
	 *         no entry contains the specified key.
	 */

	public Entry remove(Object key) {
		// Replace the following line with your solution.
		int index = this.compFunction(key.hashCode());
		DList entryList = this.entries[index];
		if(entryList == null)
			return null;
		for(DListNode node=entryList.front(); node != null; node = entryList.next(node)){
			Entry entry = (Entry)node.item;
			if(entry.key.hashCode() == key.hashCode() || entry.key.equals(key)){
				entryList.remove(node);
				return entry;
			}
		}
		return null;
	}

	/**
	 * Remove all entries from the dictionary.
	 */
	public void makeEmpty() {
		// Your solution here.
		for(int i=0;i<this.capacity;i++){
			DList entryList = this.entries[i];
			if(entryList != null){
				for(DListNode node=entryList.front(); node != null; node = entryList.next(node)){
					entryList.remove(node);
				}
				entryList = null;
			}
		}
	}
}
