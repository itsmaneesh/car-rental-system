package com.cr.rentalsystem.vehicle;

public interface VehicleSubType<T extends Vehicle<AvailableVehicleType>> {
	
	String getSubTypeName();
	
	int getSubTypeVolume();

}
