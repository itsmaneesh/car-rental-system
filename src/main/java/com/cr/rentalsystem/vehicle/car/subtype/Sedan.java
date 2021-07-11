package com.cr.rentalsystem.vehicle.car.subtype;

import com.cr.rentalsystem.constants.Constants;
import com.cr.rentalsystem.vehicle.VehicleSubType;
import com.cr.rentalsystem.vehicle.car.Car;

public class Sedan implements VehicleSubType<Car>{

	@Override
	public String getSubTypeName() {
		return Constants.SEDAN;
	}

	@Override
	public int getSubTypeVolume() {
		return Constants.SEDAN_VOLUME;
	}

}
