import java.io.Console;

import scheduler.ProcessConsumer;
import scheduler.ProcessProducer;
import scheduler.ProcessScheduleViewer;

import scheduler.scheduling.policies.FirstComeFirstServedPolicy;
import scheduler.scheduling.policies.LastComeFirstServedPolicy;
import scheduler.scheduling.policies.PriorityPolicy;
import scheduler.scheduling.policies.Policy;
import scheduler.scheduling.policies.RandomPolicy;
import scheduler.scheduling.policies.RoundRobinPolicy;

/**
 * ProcessScheduler Class: Validates the initial parameters and starts the process scheduling.
 * @author Kevin Lorenzo
 */
public class ProcessScheduler {

	/**
	 * Constant used to represents the FirstComeFirstServed Policy flag.
	 */
	private static final String FIRST_COME_FIRST_SERVED_POLICY = "-fcfs";

	/**
	 * Constant used to represents the LastComeFirstServed Policy flag.
	 */
	private static final String LAST_COME_FIRST_SERVED_POLICY = "-lcfs";

	/**
	 * Constant used to represents the Priority Policy flag.
	 */
	private static final String PRIORITY_POLICY = "-pp";

	/**
	 * Constant used to represents the Random Policy flag.
	 */
	private static final String RANDOM_POLICY = "-rand";

	/**
	 * Constant used to represents the RoundRobin Policy flag.
	 */
	private static final String ROUND_ROBIN_POLICY = "-rr";

	/**
	 * This fields represent the time of the different processes.
	 */

	private static final String HELP = "-help";

	/**
	 * This flag shows the differents comands to use it;
	*/

	private static int entryTimeA, entryTimeB, arithmeticProcessTime, ioProcessTime, conditionalProcessTime, loopProcessTime, quantumTime;

	/**
	 * Main method used in all Java programs to start the execution.
	 * @param args: Initial arguments received when the program starts.
	 */
	public static void main(String[] args){

		StringBuilder errorMessage = new StringBuilder("\n>>> ERROR : Invalid Parameters. Please, execute \"java ProcessScheduler -help\" to get more information.");

		String flagPolicy = "";

		// Parameters Validation: Checks Flag Policy and Times.

		boolean isAllValid = false;

		flagPolicy = args[0];

		if(flagPolicy.equals(HELP)){
			helpannouncer();
			System.exit(0);
		}else{
			if((args.length == 6) || (args.length == 7)) {
			flagPolicy = args[0];
				if(flagPolicy.equals(FIRST_COME_FIRST_SERVED_POLICY) || flagPolicy.equals(LAST_COME_FIRST_SERVED_POLICY) ||
					flagPolicy.equals(PRIORITY_POLICY) || flagPolicy.equals(RANDOM_POLICY) || flagPolicy.equals(ROUND_ROBIN_POLICY)) {

					try {

						arithmeticProcessTime = (int)(Float.parseFloat(args[2]) * 1000);
						ioProcessTime = (int)(Float.parseFloat(args[3]) * 1000);
						conditionalProcessTime = (int)(Float.parseFloat(args[4]) * 1000);
						loopProcessTime = (int)(Float.parseFloat(args[5]) * 1000);

						isAllValid = true;

						String[] entryTimeRange = args[1].split("-");

						if(entryTimeRange.length == 2) {
							entryTimeA = (int)(Float.parseFloat(entryTimeRange[0]) * 1000);
							entryTimeB = (int)(Float.parseFloat(entryTimeRange[1]) * 1000);
							if(!(entryTimeB > entryTimeA)) {
								isAllValid = false; // Because The Entry Time Range Could Be Invalid
							}
						} else {
							isAllValid = false;
						}

						if(flagPolicy.equals(ROUND_ROBIN_POLICY)) {
							if(args.length == 7) {
								quantumTime = (int)(Float.parseFloat(args[6]) * 1000);
							} else {
								isAllValid = false;
							}
						} else if(args.length != 6) {
							isAllValid = false;
						}

					} catch(NumberFormatException nfe) {
						isAllValid = false; // Invalid Times
					}

				}

			}

		}


		if(!isAllValid) {
			System.out.println(errorMessage);
			System.exit(1);
		}

		// Successful Validation: Executes the required operation.

		switch(flagPolicy) {
			case FIRST_COME_FIRST_SERVED_POLICY:
				ProcessScheduleViewer.currentPolicyName = "First-Come-First-Served Policy";
				startSimulation(new FirstComeFirstServedPolicy());
				break;
			case LAST_COME_FIRST_SERVED_POLICY:
				ProcessScheduleViewer.currentPolicyName = "Last-Come-First-Served Policy";
				startSimulation(new LastComeFirstServedPolicy());
				break;
			case PRIORITY_POLICY:
				ProcessScheduleViewer.currentPolicyName = "Priority Policy";
				startSimulation(new PriorityPolicy());
				break;
			case RANDOM_POLICY:
				ProcessScheduleViewer.currentPolicyName = "Random Policy";
				startSimulation(new RandomPolicy());
				break;
			case ROUND_ROBIN_POLICY:
				ProcessScheduleViewer.currentPolicyName = "Round-Robin Policy";
				startSimulation(new RoundRobinPolicy(quantumTime));
				break;
		}

	}

	/**
	 * Creates two threads. The first thread starts creating and adding random processes.
	 * The second thread starts serving the processes depending of the policy.
	 */
	public static void startSimulation(Policy policy) {

		ProcessScheduleViewer viewer = new ProcessScheduleViewer();
		viewer.setVisible(true);

 		// Thread Process Producer
		new Thread(new ProcessProducer(viewer, policy, entryTimeA, entryTimeB, arithmeticProcessTime, ioProcessTime,
 			conditionalProcessTime, loopProcessTime)).start();

 		// Thread Process Consumer
		new Thread(new ProcessConsumer(viewer, policy)).start();

	}

	public static void helpannouncer(){
		System.out.println("\n----------------------------------------------------------------------------------------------\n");
		System.out.println("Process Scheduler: To execute a task, use someone of the next comands: ");
		System.out.println("\n----------------------------------------------------------------------------------------------\n");
		System.out.println("java -fcfs entryTimeRange(x-x) entryTimeA entryTimeB entryTimeC entryTimeD ");
		System.out.println(">>> Creates a First-Come-First-Served policy that has the arithmetic, input/output, conditional, and lopp process, with the times that the user gives ");
		System.out.println("java -lcfs entryTimeRange(x-x) entryTimeA entryTimeB entryTimeC entryTimeD ");
		System.out.println(">>> Creates a Last-Come-First-Served policy that has the arithmetic, input/output, conditional, and lopp process, with the times that the user gives ");
		System.out.println("java -rr entryTimeRange(x-x) entryTimeA entryTimeB entryTimeC entryTimeD ");
		System.out.println(">>> Creates a  Round-Robin policy that has the arithmetic, input/output, conditional, and lopp process, with the times that the user gives ");
		System.out.println("java -pp entryTimeRange(x-x) entryTimeA entryTimeB entryTimeC entryTimeD ");
		System.out.println(">>> Creates a Priority-Policy that has the arithmetic, input/output, conditional, and lopp process, with the times that the user gives ");
		System.out.println("java -rand entryTimeRange(x-x) entryTimeA entryTimeB entryTimeC entryTimeD ");
		System.out.println(">>> Creates Randoms Policy that has the arithmetic, input/output, conditional, and lopp process, with the times that the user gives ");
		System.out.println("\n----------------------------------------------------------------------------------------------\n");
		System.out.println("To add a quantum Time proces:");
		System.out.println("java -flag entryTimeA entryTimeB entryTimeC entryTimeD entryQuantumTime");
		System.out.println(">>> Creates a policy with one more quantum Time");
		System.out.println("\n----------------------------------------------------------------------------------------------\n\n");

	}

}
