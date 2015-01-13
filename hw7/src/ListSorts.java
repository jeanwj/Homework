/* ListSorts.java */

import java.util.Random;

import list.*;

public class ListSorts {

	private final static int SORTSIZE = 1000;

	/**
	 * makeQueueOfQueues() makes a queue of queues, each containing one item of
	 * q. Upon completion of this method, q is empty.
	 * 
	 * @param q
	 *            is a LinkedQueue of objects.
	 * @return a LinkedQueue containing LinkedQueue objects, each of which
	 *         contains one object from q.
	 **/
	public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
		// Replace the following line with your solution.
		try {
			LinkedQueue queues = new LinkedQueue();
			while (q.size() > 0) {
				LinkedQueue queue = new LinkedQueue();
				queue.enqueue(q.dequeue());
				queues.enqueue(queue);
			}
			return queues;
		} catch (QueueEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * mergeSortedQueues() merges two sorted queues into a third. On completion
	 * of this method, q1 and q2 are empty, and their items have been merged
	 * into the returned queue.
	 * 
	 * @param q1
	 *            is LinkedQueue of Comparable objects, sorted from smallest to
	 *            largest.
	 * @param q2
	 *            is LinkedQueue of Comparable objects, sorted from smallest to
	 *            largest.
	 * @return a LinkedQueue containing all the Comparable objects from q1 and
	 *         q2 (and nothing else), sorted from smallest to largest.
	 **/
	public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
		// Replace the following line with your solution.
		try {
			LinkedQueue queue = new LinkedQueue();
			Comparable item1, item2;
			while (q1.size() > 0 && q2.size() > 0) {
				item1 = (Comparable) q1.front();
				item2 = (Comparable) q2.front();
				if (item1.compareTo(item2) < 0) {
					queue.enqueue(q1.dequeue());
				} else {
					queue.enqueue(q2.dequeue());
				}
			}
			LinkedQueue notEmptyQueue = q1.size() > 0 ? q1 : q2;
			queue.append(notEmptyQueue);
			return queue;
		} catch (QueueEmptyException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * partition() partitions qIn using the pivot item. On completion of this
	 * method, qIn is empty, and its items have been moved to qSmall, qEquals,
	 * and qLarge, according to their relationship to the pivot.
	 * 
	 * @param qIn
	 *            is a LinkedQueue of Comparable objects.
	 * @param pivot
	 *            is a Comparable item used for partitioning.
	 * @param qSmall
	 *            is a LinkedQueue, in which all items less than pivot will be
	 *            enqueued.
	 * @param qEquals
	 *            is a LinkedQueue, in which all items equal to the pivot will
	 *            be enqueued.
	 * @param qLarge
	 *            is a LinkedQueue, in which all items greater than pivot will
	 *            be enqueued.
	 **/
	public static void partition(LinkedQueue qIn, Comparable pivot,
			LinkedQueue qSmall, LinkedQueue qEquals, LinkedQueue qLarge) {
		// Your solution here.
		try {
			while (qIn.size() > 0) {
				Comparable item = (Comparable) qIn.dequeue();
				if (item.compareTo(pivot) < 0) {
					qSmall.enqueue(item);
				} else if (item.compareTo(pivot) > 0) {
					qLarge.enqueue(item);
				} else {
					qEquals.enqueue(item);
				}
			}
		} catch (QueueEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * mergeSort() sorts q from smallest to largest using mergesort.
	 * 
	 * @param q
	 *            is a LinkedQueue of Comparable objects.
	 **/
	public static void mergeSort(LinkedQueue q) {
		// Your solution here.
		if (q.size() <= 1)
			return;

		LinkedQueue queues = makeQueueOfQueues(q);
		try {
			while (queues.size() > 1) {
				LinkedQueue q1 = (LinkedQueue) queues.dequeue();
				LinkedQueue q2 = (LinkedQueue) queues.dequeue();
				queues.enqueue(mergeSortedQueues(q1, q2));
			}
			q.append((LinkedQueue) queues.dequeue());
		} catch (QueueEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * quickSort() sorts q from smallest to largest using quicksort.
	 * 
	 * @param q
	 *            is a LinkedQueue of Comparable objects.
	 **/
	public static void quickSort(LinkedQueue q) {
		// Your solution here.
		if (q.size() <= 1)
			return;

		LinkedQueue qSmall = new LinkedQueue();
		LinkedQueue qEquals = new LinkedQueue();
		LinkedQueue qLarge = new LinkedQueue();

		Random random = new Random();
		int povitIndex = random.nextInt(q.size() + 1);
		partition(q, (Comparable) q.nth(povitIndex), qSmall, qEquals, qLarge);

		if (qSmall.size() > 1) {
			quickSort(qSmall);
		}
		if (qSmall.size() > 1) {
			quickSort(qLarge);
		}

		q.append(qSmall);
		q.append(qEquals);
		q.append(qLarge);
	}

	/**
	 * makeRandom() builds a LinkedQueue of the indicated size containing
	 * Integer items. The items are randomly chosen between 0 and size - 1.
	 * 
	 * @param size
	 *            is the size of the resulting LinkedQueue.
	 **/
	public static LinkedQueue makeRandom(int size) {
		LinkedQueue q = new LinkedQueue();
		for (int i = 0; i < size; i++) {
			q.enqueue(new Integer((int) (size * Math.random())));
		}
		return q;
	}

	/**
	 * main() performs some tests on mergesort and quicksort. Feel free to add
	 * more tests of your own to make sure your algorithms works on boundary
	 * cases. Your test code will not be graded.
	 **/
	public static void main(String[] args) {

		LinkedQueue q = makeRandom(10);
		
		System.out.println("common cases: ");
		System.out.println("before merge sort" + q.toString());
		mergeSort(q);
		System.out.println("after merge sort " + q.toString());

		q = makeRandom(10);
		System.out.println("before quick sort" + q.toString());
		quickSort(q);
		System.out.println("after quick sort " + q.toString());

		
		System.out.println("\ncases for list of size zero: ");
		q = makeRandom(0);
		System.out.println("before merge sort" + q.toString());
		mergeSort(q);
		System.out.println("after merge sort " + q.toString());

		q = makeRandom(0);
		System.out.println("before quick sort" + q.toString());
		quickSort(q);
		System.out.println("after quick sort " + q.toString());
		
		
		System.out.println("\ncases for list of size one: ");
		q = makeRandom(1);
		System.out.println("before merge sort" + q.toString());
		mergeSort(q);
		System.out.println("after merge sort " + q.toString());

		q = makeRandom(1);
		System.out.println("before quick sort" + q.toString());
		quickSort(q);
		System.out.println("after quick sort " + q.toString());

		/*
		 * Remove these comments for Part III.
		 */
		System.out.println("\nbenchmark :");
		int size = 10;
		for (int i = 0; i < 6; i++, size *= 10) {
			Timer stopWatch = new Timer();
			q = makeRandom(size);
			stopWatch.start();
			mergeSort(q);
			stopWatch.stop();
			System.out.println("Mergesort time, " + size + " Integers:  " + stopWatch.elapsed() + " msec.");

			stopWatch.reset();
			q = makeRandom(size);
			stopWatch.start();
			quickSort(q);
			stopWatch.stop();
			System.out.println("Quicksort time, " + size + " Integers:  " + stopWatch.elapsed() + " msec.");
		}
	}

}
