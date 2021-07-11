package com.cr.rentalsystem.vehicle.car;

import com.cr.rentalsystem.vehicle.AvailableVehicleType;
import com.cr.rentalsystem.vehicle.Vehicle;

/**
 * Vehicle - CAR
 * 
 * @author maneesh.chintha
 *
 */
public class Car implements Vehicle<AvailableVehicleType>{

	@Override
	public AvailableVehicleType getVehicleType() {
		return AvailableVehicleType.CAR;
	}


}
