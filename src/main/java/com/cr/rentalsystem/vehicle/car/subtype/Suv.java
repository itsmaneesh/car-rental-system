/**
 * 
 */
package com.cr.rentalsystem.vehicle.car.subtype;

import com.cr.rentalsystem.constants.Constants;
import com.cr.rentalsystem.vehicle.VehicleSubType;
import com.cr.rentalsystem.vehicle.car.Car;

/**
 * @author maneesh.chintha
 *
 */
public class Suv implements VehicleSubType<Car>{

	@Override
	public String getSubTypeName() {
		return Constants.SUV;
	}

	@Override
	public int getSubTypeVolume() {
		return Constants.SUV_VOLUME;
	}

}
