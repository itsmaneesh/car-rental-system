package com.cr.rentalsystem.vehicle;

/**
 * 
 * @author maneesh.chintha
 *
 * @param <T>
 */
public interface Vehicle<T extends Enum<AvailableVehicleType>> {
	
	/**
	 * get vehicle type
	 * @return <code>AvailableVehicleType</code> vehicleType
	 */
	T getVehicleType();

}
