package scheduler.scheduling.policies;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import scheduler.ProcessScheduleViewer;
import scheduler.processing.SimpleProcess;

/**
 * FirstComeFirstServedPolicy Class: Represents a policy that uses a FIFO data structure.
 * In this type of policy, the first process that come in, is the first that is served.
 * The java.util.concurrent.ConcurrentLinkedQueue class provided by the Java Programming Language is used.
 * @author Kevin Lorenzo
 */
public class FirstComeFirstServedPolicy extends Policy {

	/**
	 * This is a lock, an object used for secure access concurrently.
	 */
	private Lock lock = new ReentrantLock();

	/**
	 * This field is a FIFO data structure which is used to save processes.
	 */
	private ConcurrentLinkedQueue<SimpleProcess> queue = new ConcurrentLinkedQueue<>();

	/**
	 * Adds a new process in the queue.
	 * @param process: The new process that will be added in the queue.
	 */
	@Override
	public void add(SimpleProcess process) {
		lock.lock();
		try {
			this.queue.add(process);
			ProcessScheduleViewer.setData(queue);
			this.size++;
			this.totalProcesses++;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Removes the first process from the queue.
	 */
	@Override
	public void remove() {
		if(!this.queue.isEmpty()) {
			this.queue.poll();
			this.size--;
		}
	}

	/**
	 * Returns the next process available in the queue. If the queue is empty, returns null.
	 * @return The next process. Returns null if the queue is empty.
	 */
	@Override
	public SimpleProcess next() {
		SimpleProcess nextProcess = null;
		lock.lock();
		try {
			if(!this.queue.isEmpty()) {
				nextProcess = this.queue.peek();
				this.remove();
			}
		} finally {
			lock.unlock();
		}
		return nextProcess;
	}

}
