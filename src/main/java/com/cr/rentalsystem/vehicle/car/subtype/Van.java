package com.cr.rentalsystem.vehicle.car.subtype;

import com.cr.rentalsystem.constants.Constants;
import com.cr.rentalsystem.vehicle.VehicleSubType;
import com.cr.rentalsystem.vehicle.car.Car;

public class Van implements VehicleSubType<Car>{
	
	@Override
	public String getSubTypeName() {
		return Constants.VAN;
	}

	@Override
	public int getSubTypeVolume() {
		return Constants.VAN_VOLUME;
	}
}
