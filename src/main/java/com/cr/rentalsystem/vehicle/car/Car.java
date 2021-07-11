package com.cr.rentalsystem.vehicle.car;

import com.cr.rentalsystem.vehicle.AvailableVehicleType;
import com.cr.rentalsystem.vehicle.Vehicle;

public class Car implements Vehicle<AvailableVehicleType>{

	@Override
	public AvailableVehicleType getVehicleType() {
		return AvailableVehicleType.CAR;
	}


}
