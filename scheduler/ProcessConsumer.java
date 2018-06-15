package scheduler;

import scheduler.processing.ComplexProcess;
import scheduler.processing.SimpleProcess;
import scheduler.scheduling.policies.Policy;
import scheduler.scheduling.policies.RoundRobinPolicy;

/**
 * ProcessConsumer Class: Emulates the processing of the processes that are currently available.
 * @author Kevin Lorenzo
 */
public class ProcessConsumer implements Runnable {

	private ProcessScheduleViewer viewer;

	/**
	 * This field represents the policy that is used to serve the processes.
	 */
	private Policy policy;

	/**
	 * Initializes the ProcessConsumer with the policy received as parameter.
	 * @param policy: This field represents the policy that is used to serve the processes.
	 */
	public ProcessConsumer(ProcessScheduleViewer viewer, Policy policy) {
		this.viewer = viewer;
		this.policy = policy;
	}

	/**
	 * Method which is executed when the thread starts. Emulates the processing of the processes that are currently available.
	 */
	@Override
	public void run() {
		int completedProcesses = 0;
		while(viewer.getSimulationState()) {
			SimpleProcess nextProcess = this.policy.next();
			if(nextProcess != null) {
				try {

					viewer.setCurrentProcess("Currently Serving The Process:   [ ID ]: " + nextProcess.getId() + "   [ Type ]: " +
						((ComplexProcess)nextProcess).getType());

					int processingTime = ((ComplexProcess)nextProcess).getProcessingTime();

					// Only for Round-Robin Policy
					if(this.policy instanceof RoundRobinPolicy) {

						RoundRobinPolicy roundRobinPolicy = (RoundRobinPolicy)policy;
						int quantumTime = roundRobinPolicy.getQuantumTime();

						// Checks if the Processing Time is greater that Quantum Time.
						if(processingTime >= quantumTime) {
							Thread.sleep(quantumTime);
						} else {
							Thread.sleep(processingTime);
						}

						// If the new Processing Time is major than zero, then adds the same process to the list.
						if((processingTime - quantumTime) > 0) {
							((ComplexProcess)nextProcess).setProcessingTime(processingTime - quantumTime);
							this.policy.add(nextProcess);
						// If the new Processing Time is less than zero, then the process is completed and removed of the list.
						} else {
							completedProcesses++;
							viewer.setProcessCompleted("Process Completed:   [ ID ]: " + nextProcess.getId() + "   [ Type ]: " +
								((ComplexProcess)nextProcess).getType(), completedProcesses);
						}

					} else {
						Thread.sleep(processingTime);
						completedProcesses++;
						viewer.setProcessCompleted("Process Completed:   [ ID ]: " + nextProcess.getId() + "   [ Type ]: " +
							((ComplexProcess)nextProcess).getType(), completedProcesses);
					}

				} catch(InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}
	}

}
