package scheduler.scheduling.policies;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList;

import scheduler.ProcessScheduleViewer;
import scheduler.processing.ComplexProcess;
import scheduler.processing.SimpleProcess;

/**
 * PriorityPolicy Class: Represents a policy that uses a LinkedList data structure.
 * In this type of policy, the process with major priority is the first that is served.
 * The java.util.LinkedList class provided by the Java Programming Language is used.
 * @author Kevin Lorenzo
 */
public class PriorityPolicy extends Policy {

	/**
	 * This is a lock, an object used for secure access concurrently.
	 */
	private Lock lock = new ReentrantLock();

	/**
	 * This field is a Linked List which is used to save processes.
	 */
	private LinkedList<SimpleProcess> linkedList = new LinkedList<>();

	/**
	 * Adds a new process in the linked list.
	 * @param process: The new process that will be added in the linked list.
	 */
	@Override
	public void add(SimpleProcess process) {
		lock.lock();
		try {
			this.linkedList.add(process);
			// Sorts the processes depending by its type and priority.
			if(this.linkedList.size() > 1) {
				this.linkedListInsertionSort();
			}
			ProcessScheduleViewer.setData(linkedList);
			this.size++;
			this.totalProcesses++;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Removes the first process from the linked list.
	 */
	@Override
	public void remove() {
		if(!this.linkedList.isEmpty()) {
			this.linkedList.poll();
			this.size--;
		}
	}

	/**
	 * Returns the next process available in the linked list. If the linked list is empty, returns null.
	 * @return The next process. Returns null if the linked list is empty.
	 */
	@Override
	public SimpleProcess next() {
		SimpleProcess nextProcess = null;
		lock.lock();
		try {
			if(!this.linkedList.isEmpty()) {
				nextProcess = this.linkedList.peek();
				this.remove();
			}
		} finally {
			lock.unlock();
		}
		return nextProcess;
	}

	/**
	 * Returns an integer that represents the priority of the process depending by its type.
	 * @param process: Process which its type will be obtained.
	 * @return The priority of the process depending by its type.
	 */
	public int getPriorityByProcessType(SimpleProcess process) {
		String type = ((ComplexProcess)process).getType();
		int priority = 0;
		switch(type) {
			case "IO":
				priority = 1; break;
			case "A":
				priority = 2; break;
			case "C":
				priority = 3; break;
			case "L":
				priority = 4; break;
		}
		return priority;
	}

	/**
	 * Sorts the processes of the linked list. First, sorts the processes by priority. Second, sorts the processes by process type.
	 */
	public void linkedListInsertionSort() {
		// Sorts by priority
		for(int i = 1; i < this.linkedList.size(); i++) {
			SimpleProcess aux = this.linkedList.get(i);
			int j = i - 1;
			while((j >= 0) && (((ComplexProcess)this.linkedList.get(j)).getPriority() > ((ComplexProcess)aux).getPriority())) {
				this.linkedList.set(j + 1, this.linkedList.get(j));
				j--;
			}
			this.linkedList.set(j + 1, aux);
		}
		// Sorts by process type
		for(int i = 1; i < this.linkedList.size(); i++) {
			SimpleProcess aux = this.linkedList.get(i);
			int j = i - 1;
			while((j >= 0) && (this.getPriorityByProcessType(this.linkedList.get(j)) > this.getPriorityByProcessType(aux))) {
				this.linkedList.set(j + 1, this.linkedList.get(j));
				j--;
			}
			this.linkedList.set(j + 1, aux);
		}
	}

}
