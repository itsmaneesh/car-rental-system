package com.cr.rentalsystem.vehicle;

import java.util.List;

/**
 * @author maneesh.chintha
 *
 */
public interface RentalVehicle<T extends Vehicle<AvailableVehicleType>> {
	
	T getVehicle();
	
	List<VehicleSubType<T>> getVehicleSubType();

}
