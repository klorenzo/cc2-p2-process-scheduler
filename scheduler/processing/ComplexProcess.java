package scheduler.processing;

/**
 * ComplexProcess Interface: Implemented by processes that need to have priority, processing time and type.
 * @author Kevin Lorenzo
 */
public interface ComplexProcess {

	/**
	 * Returns the process priority.
	 * @return The process priority.
	 */
	public int getPriority();

	/**
	 * Returns the processing time.
	 * @return The processing time.
	 */
	public int getProcessingTime();

	/**
	 * Sets a new processing time.
	 * @param processingTime: The new processing time.
	 */
	public void setProcessingTime(int processingTime);

	/**
	 * Returns the process type. "A" represents ArithmeticProcess. "IO" represents IOProcess.
	 * "C" represents ConditionalProcess. And "L" represents LoopProcess.
	 * @return The process type.
	 */
	public String getType();

}
