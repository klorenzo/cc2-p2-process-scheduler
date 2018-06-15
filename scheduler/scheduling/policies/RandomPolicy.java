package scheduler.scheduling.policies;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;
import java.util.Stack;

import scheduler.ProcessScheduleViewer;
import scheduler.processing.SimpleProcess;

/**
 * RandomPolicy Class: In this type of policy, a random process is selected to be served.
 * The java.util.Stack class provided by the Java Programming Language is used, because it
 * let us to manipulate the elements using its index.
 * @author Kevin Lorenzo
 */
public class RandomPolicy extends Policy {

	/**
	 * This is a lock, an object used for secure access concurrently.
	 */
	private Lock lock = new ReentrantLock();

	/**
	 * This field is a LIFO data structure which is used to save processes.
	 */
	private Stack<SimpleProcess> stack = new Stack<>();

	/**
	 * This field is a java.util.Random object which is used to get random numbers.
	 */
	private Random random = new Random();

	/**
	 * This field is an integer that represents the index of the next random process which will be served.
	 */
	private int nextProcessIndex;

	/**
	 * Adds a new process in the stack.
	 * @param process: The new process that will be added in the stack.
	 */
	@Override
	public void add(SimpleProcess process) {
		lock.lock();
		try {
			this.stack.push(process);
			ProcessScheduleViewer.setData(stack);
			this.size++;
			this.totalProcesses++;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Removes the first process from the stack.
	 */
	@Override
	public void remove() {
		if(!this.stack.isEmpty()) {
			this.stack.remove(this.nextProcessIndex);
			this.size--;
		}
	}

	/**
	 * Returns the next process available in the stack. If the stack is empty, returns null.
	 * @return The next process. Returns null if the stack is empty.
	 */
	@Override
	public SimpleProcess next() {
		SimpleProcess nextProcess = null;
		lock.lock();
		try {
			if(!this.stack.isEmpty()) {
				this.nextProcessIndex = this.random.nextInt(this.stack.size());
				nextProcess = this.stack.get(this.nextProcessIndex);
				this.remove();
			}
		} finally {
			lock.unlock();
		}
		return nextProcess;
	}

}
