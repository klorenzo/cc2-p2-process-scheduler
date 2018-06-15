package scheduler;

import java.util.Random;

import scheduler.processing.ArithmeticProcess;
import scheduler.processing.ConditionalProcess;
import scheduler.processing.IOProcess;
import scheduler.processing.LoopProcess;

import scheduler.scheduling.policies.Policy;

/**
 * ProcessProducer Class: Creates and adds new processes to the list.
 * @author Kevin Lorenzo
 */
public class ProcessProducer implements Runnable {

	/**
	 * This field represents the Process Schedule Viewer.
	 */
	private ProcessScheduleViewer viewer;

	/**
	 * This field represents the policy that is used to serve the processes.
	 */
	private Policy policy;

	/**
	 * This fields represent the time of the different processes.
	 */
	private int entryTimeA, entryTimeB, arithmeticProcessTime, ioProcessTime, conditionalProcessTime, loopProcessTime;

	/**
	 * Initializes the ProcessProducer with the policy and times received as parameters.
	 * @param viewer: This field represents the Process Schedule Viewer.
	 * @param policy: This field represents the policy that is used to serve the processes.
	 * @param entryTimeA: Time range to add new processes.
	 * @param entryTimeB: Time range to add new processes.
	 * @param arithmeticProcessTime: Processing time for Arithmetic Processes.
	 * @param ioProcessTime: Processing time for IO Processes.
	 * @param conditionalProcessTime: Processing time for Conditional Processes.
	 * @param loopProcessTime: Processing time for Loop Processes.
	 */
	public ProcessProducer(ProcessScheduleViewer viewer, Policy policy, int entryTimeA, int entryTimeB, int arithmeticProcessTime, int ioProcessTime,
		int conditionalProcessTime, int loopProcessTime) {
		this.viewer = viewer;
		this.policy = policy;
		this.entryTimeA = entryTimeA;
		this.entryTimeB = entryTimeB;
		this.arithmeticProcessTime = arithmeticProcessTime;
		this.ioProcessTime = ioProcessTime;
		this.conditionalProcessTime = conditionalProcessTime;
		this.loopProcessTime = loopProcessTime;
	}

	/**
	 * Method which is executed when the thread starts. Creates and adds new processes to the list.
	 */
	@Override
	public void run() {
		Random random = new Random();
		int processId = 0;
		while(viewer.getSimulationState()) {

			// Random Processes
			int randomPriority = random.nextInt(100) + 1;
			switch(random.nextInt(4)) {
				case 0:
					this.policy.add(new ArithmeticProcess(++processId, randomPriority, this.arithmeticProcessTime));
					viewer.setNewProcessAdded("New Process Added:   [ ID ]: " + processId + "   [ Type ]: Arithmetic" + "   [ Priority ]: " + randomPriority, this.policy.totalProcesses());
					break;
				case 1:
					this.policy.add(new IOProcess(++processId, randomPriority, this.ioProcessTime));
					viewer.setNewProcessAdded("New Process Added:   [ ID ]: " + processId + "   [ Type ]: IO" + "   [ Priority ]: " + randomPriority, this.policy.totalProcesses());
					break;
				case 2:
					this.policy.add(new ConditionalProcess(++processId, randomPriority, this.conditionalProcessTime));
					viewer.setNewProcessAdded("New Process Added:   [ ID ]: " + processId + "   [ Type ]: Conditional" + "   [ Priority ]: " + randomPriority, this.policy.totalProcesses());
					break;
				case 3:
					this.policy.add(new LoopProcess(++processId, randomPriority, this.loopProcessTime));
					viewer.setNewProcessAdded("New Process Added:   [ ID ]: " + processId + "   [ Type ]: Loop" + "   [ Priority ]: " + randomPriority, this.policy.totalProcesses());
					break;
			}

			try {
				// Entry Time Range
				Thread.sleep(random.nextInt(this.entryTimeB - this.entryTimeA) + this.entryTimeA + 1);
			} catch(InterruptedException ie) {
				ie.printStackTrace();
			}

		}
	}

}
