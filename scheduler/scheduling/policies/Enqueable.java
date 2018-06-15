/** 
	Interfaz que deben implementar todas las clases que quieren
        comportarse como politicas de calendarizacion de procesos.

	@author Ing. Andrea Quan
**/
	
package scheduler.scheduling.policies;

import scheduler.processing.SimpleProcess;
	
public interface Enqueable {
	/** ingresa un proceso a la cola de procesos de la politica
            @param p Proceso a ingresar en la cola de la politica 
        **/
	public void add(SimpleProcess p);
	/** Remueve un proceso de la cola de procesos (el siguiente a
            ser atendido 
        **/
	public void remove();
	
	/** Devuelve el siguiente proceso a ser atendido. No lo remueve de la
            cola
            @return devuelve la instancia de el SimpleProcess siguiente a 
            ser atendido. 
        **/
	public SimpleProcess next();
}