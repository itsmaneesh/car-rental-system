package com.cr.rentalsystem.vehicle;

import java.util.ArrayList;
import java.util.List;

import com.cr.rentalsystem.vehicle.car.Car;
import com.cr.rentalsystem.vehicle.car.subtype.Sedan;
import com.cr.rentalsystem.vehicle.car.subtype.Suv;
import com.cr.rentalsystem.vehicle.car.subtype.Van;

public class RentalCar implements RentalVehicle<Car> {
	
	@Override
	public Car getVehicle() {
		Car car = new Car();
		return car;
	}

	@Override
	public List<VehicleSubType<Car>> getVehicleSubType() {
		List<VehicleSubType<Car>> subTypeList = new ArrayList<>();
		Sedan sedan = new Sedan();
		Suv suv = new Suv();
		Van van = new Van();
		subTypeList.add(sedan);
		subTypeList.add(suv);
		subTypeList.add(van);
		return subTypeList;
	}
	
}
