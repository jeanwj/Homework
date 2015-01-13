/* Sorts.java */

package sort;

public class Sorts {

	/**
	 * Place any final static fields you would like to have here.
	 **/
	private final static int MARK = 0x0F;

	private static int getDigit(int key, int whichDigit){
		return (key >> (whichDigit * 4) & MARK);
	}
	
	/**
	 * countingSort() sorts an array of int keys according to the values of
	 * _one_ of the base-16 digits of each key. "whichDigit" indicates which
	 * digit is the sort key. A zero means sort on the least significant (ones)
	 * digit; a one means sort on the second least significant (sixteens) digit;
	 * and so on, up to a seven, which means sort on the most significant digit.
	 * 
	 * @param key
	 *            is an array of ints. Assume no key is negative.
	 * @param whichDigit
	 *            is a number in 0...7 specifying which base-16 digit is the
	 *            sort key.
	 * @return an array of type int, having the same length as "keys" and
	 *         containing the same keys sorted according to the chosen digit.
	 * 
	 *         Note: Return a _newly_ created array. DO NOT CHANGE THE ARRAY
	 *         keys.
	 **/
	public static int[] countingSort(int[] keys, int whichDigit) {
		// Replace the following line with your solution.
		if(whichDigit < 0 || whichDigit > 7)
			throw new java.lang.IllegalArgumentException("whichDigit ����  0 ~ 7 �ķ�Χ��");
		
		int length = keys.length;
		
		int[] digits = new int[length];
		int max = digits[0] = getDigit(keys[0] ,whichDigit);
		for(int i = 1; i < length; i++) {
			digits[i] = getDigit(keys[i] ,whichDigit);
			max = digits[i] > max ? digits[i] : max;
		}
		
		int[] counts = new int[max + 1];
		for(int i = 0; i < length; i++){
			counts[digits[i]]++;
		}
		
		for(int i = 1; i < counts.length; i++){
			counts[i] += counts[i-1];
		}
		
		int[] sortedArray = new int[length];
		for(int i = length - 1;i >= 0; i--){
			sortedArray[--counts[digits[i]]] = keys[i];
		}
		return sortedArray;
	}

	/**
	 * radixSort() sorts an array of int keys (using all 32 bits of each key to
	 * determine the ordering).
	 * 
	 * @param key
	 *            is an array of ints. Assume no key is negative.
	 * @return an array of type int, having the same length as "keys" and
	 *         containing the same keys in sorted order.
	 * 
	 *         Note: Return a _newly_ created array. DO NOT CHANGE THE ARRAY
	 *         keys.
	 **/
	public static int[] radixSort(int[] keys) {
		// Replace the following line with your solution.
		int[] sortedArray = (int[]) keys.clone();
		for(int i = 0; i < 8; i++){
			sortedArray = countingSort(sortedArray, i);
		}
		return sortedArray;
	}

	/**
	 * yell() prints an array of int keys. Each key is printed in hexadecimal
	 * (base 16).
	 * 
	 * @param key
	 *            is an array of ints.
	 **/
	public static void yell(int[] keys) {
		System.out.print("keys are [ ");
		for (int i = 0; i < keys.length; i++) {
			System.out.print(Integer.toString(keys[i], 16) + " ");
		}
		System.out.println("]");
	}

	/**
	 * main() creates and sorts a sample array. We recommend you add more tests
	 * of your own. Your test code will not be graded.
	 **/
	public static void main(String[] args) {
		int[] keys = { Integer.parseInt("60013879", 16),
				Integer.parseInt("11111119", 16),
				Integer.parseInt("2c735010", 16),
				Integer.parseInt("2c732010", 16),
				Integer.parseInt("7fffffff", 16),
				Integer.parseInt("4001387c", 16),
				Integer.parseInt("10111119", 16),
				Integer.parseInt("529a7385", 16),
				Integer.parseInt("1e635010", 16),
				Integer.parseInt("28905879", 16),
				Integer.parseInt("00011119", 16),
				Integer.parseInt("00000000", 16),
				Integer.parseInt("7c725010", 16),
				Integer.parseInt("1e630010", 16),
				Integer.parseInt("111111e5", 16),
				Integer.parseInt("61feed0c", 16),
				Integer.parseInt("3bba7387", 16),
				Integer.parseInt("52953fdb", 16),
				Integer.parseInt("40013879", 16) };

		yell(keys);
		keys = radixSort(keys);
		//keys = countingSort(keys, 1);
		yell(keys);
	}

}
