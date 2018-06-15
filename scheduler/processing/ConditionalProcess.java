package scheduler.processing;

import scheduler.processing.ComplexProcess;

/**
 * ConditionalProcess Class: Represents a Conditional operation.
 * @author Kevin Lorenzo
 */
public class ConditionalProcess extends SimpleProcess implements ComplexProcess {

	/**
	 * This field is a String Constant that represents the process type. For this type of process, the string "C" is used.
	 */
	private static final String type = "C";

	/**
	 * This field is an integer that represents the process priority.
	 */
	private int priority;

	/**
	 * This field is an integer that represents the processing time in milliseconds.
	 */
	private int processingTime;

	/**
	 * Initializes the process with the ID, priority, and processing time, received as parameters.
	 * @param id: This field is an integer that identifies the process.
	 * @param priority: This field is an integer that represents the process priority.
	 * @param processingTime: This field is an integer that represents the processing time.
	 */
	public ConditionalProcess(int id, int priority, int processingTime) {
		super(id);
		this.priority = priority;
		this.processingTime = processingTime;
	}

	/**
	 * Returns the process priority.
	 * @return The process priority.
	 */
	@Override
	public int getPriority() {
		return this.priority;
	}

	/**
	 * Returns the processing time.
	 * @return The processing time.
	 */
	@Override
	public int getProcessingTime() {
		return this.processingTime;
	}

	/**
	 * Sets a new processing time.
	 * @param processingTime: The new processing time.
	 */
	@Override
	public void setProcessingTime(int processingTime) {
		this.processingTime = processingTime;
	}

	/**
	 * Returns the process type. "A" represents ArithmeticProcess. "IO" represents IOProcess.
	 * "C" represents ConditionalProcess. And "L" represents LoopProcess.
	 * @return The process type.
	 */
	@Override
	public String getType() {
		return this.type;
	}

}
