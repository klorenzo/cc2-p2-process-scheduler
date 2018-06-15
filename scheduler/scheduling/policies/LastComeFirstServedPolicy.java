package scheduler.scheduling.policies;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Stack;

import scheduler.ProcessScheduleViewer;
import scheduler.processing.SimpleProcess;

/**
 * LastComeFirstServedPolicy Class: Represents a policy that uses a LIFO data structure.
 * In this type of policy, the last process that come in, is the first that is served.
 * The java.util.Stack class provided by the Java Programming Language is used.
 * @author Kevin Lorenzo
 */
public class LastComeFirstServedPolicy extends Policy {

	/**
	 * This is a lock, an object used for secure access concurrently.
	 */
	private Lock lock = new ReentrantLock();

	/**
	 * This field is a LIFO data structure which is used to save processes.
	 */
	private Stack<SimpleProcess> stack = new Stack<>();

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
			this.stack.pop();
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
				nextProcess = this.stack.peek();
				this.remove();
			}
		} finally {
			lock.unlock();
		}
		return nextProcess;
	}

}
