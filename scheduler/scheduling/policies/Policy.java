/** 
	Esta clase representa la definicion abstracta de una politica de
        calendarizacion, la cual representa la "cola" en la que se
        debe calendarizar los procesos. Todas las clases 
        que representen tipos de politica de calendarizacion deben heredar de esta clase

	@author Ing. Andrea Quan
**/
	
package scheduler.scheduling.policies;

import scheduler.processing.SimpleProcess;
	
 public abstract class Policy implements Enqueable {
	/** El campo size va a representar el numero de procesos activos, 
	es decir, en la cola actualmente, en este momento 
        **/
	protected int size;
	/** El campo total va a representar el total de procesos que han sido ingresados a
        la cola **/
	protected int totalProcesses;
	/**
		Inicializa el Policy con size = 0 y totalProcesses = 0.
	**/		
	public Policy() {
		this.size = 0;
		this.totalProcesses = 0;
	}
        /**
		Devuelve la cantidad de procesos activos en la cola de la politica
		@return devuelve un entero que representa la cantidad de procesos
                en la cola actualmente
	**/	
	public final int size() {
		return size;
	}
        /**
		Devuelve el total de procesos ingresados a la cola de la politica
		@return devuelve el entero que representa la cantidad total
                de procesos ingresados a la cola de la politica
	**/	
	public final int totalProcesses() {
		return totalProcesses;
	}
}