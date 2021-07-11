package com.cr.rentalsystem.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.cr.rentalsystem.booking.BookingDetail;
import com.cr.rentalsystem.constants.Constants;
import com.cr.rentalsystem.customer.Customer;
import com.cr.rentalsystem.pojo.VehicleDetail;
import com.cr.rentalsystem.pojo.VehicleToBook;
import com.cr.rentalsystem.vehicle.AvailableVehicleType;
import com.cr.rentalsystem.vehicle.car.subtype.Sedan;
import com.cr.rentalsystem.vehicle.car.subtype.Suv;
import com.cr.rentalsystem.vehicle.car.subtype.Van;

public class RentalUtil {
	
	private static final List<BookingDetail> bookedVehicleDetailList = new ArrayList<>();

	public static List<BookingDetail> getBookingByReferenceNumber(String bookingRefNumber) {
		if(null == bookingRefNumber) {
			return null;
		}
		List<BookingDetail> bookingDetail = null;
		synchronized (bookedVehicleDetailList) {
			bookingDetail = bookedVehicleDetailList.stream().filter(p -> bookingRefNumber.equalsIgnoreCase(p.getBookingReferenceNumber())).collect(Collectors.toList());
		}
		return bookingDetail;
	}

	public static String bookCar(Customer customer , VehicleToBook vehicleToBook) {
		LocalDateTime fromDateTime = getDateTime(vehicleToBook.getFromDate(), vehicleToBook.getFromTime());
		LocalDateTime toDateTime = fromDateTime.plusDays(Long.parseLong(vehicleToBook.getNoOfDays()));
		
		if(null == fromDateTime || null == toDateTime) {
			return null;
		}
		
		String bookingReferenceNumber = UUID.randomUUID().toString();
		
		BookingDetail bookingDetail = new BookingDetail(customer, AvailableVehicleType.CAR.toString(), vehicleToBook.getVehicleType(), fromDateTime, toDateTime, bookingReferenceNumber);
		synchronized (bookedVehicleDetailList) {
			bookedVehicleDetailList.add(bookingDetail);
		}
		return bookingReferenceNumber;
	}

	public static LocalDateTime getDateTime(String dateStr,  String timeStr) {
		if (null == dateStr || null == timeStr) {
			return null;
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT);
		LocalDateTime localateTime = null;
		String dateTimeStr = null;
		try {
			dateTimeStr = dateStr.concat(" ").concat(timeStr);
			localateTime = LocalDateTime.parse(dateTimeStr, dtf);
			
		} catch (DateTimeParseException e) {
			System.out.println("Error in parsing date.["+dateTimeStr+"]");
		}
		return localateTime;
	}
	
	public static List<VehicleDetail> getAvailableCars() {
		
		List<VehicleDetail> vehicleDetails= new ArrayList<>();
		int bookedSedanNumber = 0;
		int bookedSuvNumber = 0;
		int bookedVanNumber = 0;
		
		int availableSedanNumber = 0;
		int availableSuvNumber = 0;
		int availableVanNumber = 0;
		
		Sedan sedan = new Sedan();
		Suv suv = new Suv();
		Van van = new Van();
		
		String vehicleType = AvailableVehicleType.CAR.toString();
		
		synchronized(bookedVehicleDetailList) {
			bookedSedanNumber = bookedVehicleDetailList.stream()
			.filter(p -> Constants.SEDAN.equalsIgnoreCase(p.getVehicleSubType()) 
					&& AvailableVehicleType.CAR.toString().equalsIgnoreCase(p.getVehicleType()))
			.collect(Collectors.toList()).size();
			
			availableSedanNumber = sedan.getSubTypeVolume() - bookedSedanNumber;
			
			bookedSuvNumber = bookedVehicleDetailList.stream()
					.filter(p -> Constants.SUV.equalsIgnoreCase(p.getVehicleSubType())
							&& AvailableVehicleType.CAR.toString().equalsIgnoreCase(p.getVehicleType()))
					.collect(Collectors.toList()).size();
			
			availableSuvNumber = suv.getSubTypeVolume() - bookedSuvNumber;
			
			bookedVanNumber = bookedVehicleDetailList.stream()
					.filter(p -> Constants.VAN.equalsIgnoreCase(p.getVehicleSubType())
							&& AvailableVehicleType.CAR.toString().equalsIgnoreCase(p.getVehicleType()))
					.collect(Collectors.toList()).size();
			availableVanNumber = van.getSubTypeVolume() - bookedVanNumber;
		}
		
		VehicleDetail sedanDetail = new VehicleDetail(vehicleType, Constants.SEDAN, availableSedanNumber) ;
		VehicleDetail suvDetail = new VehicleDetail(vehicleType, Constants.SUV, availableSuvNumber) ;
		VehicleDetail vanDetail = new VehicleDetail(vehicleType, Constants.VAN, availableVanNumber) ;
		
		vehicleDetails.add(sedanDetail);
		vehicleDetails.add(suvDetail);
		vehicleDetails.add(vanDetail);
		
		return vehicleDetails;
	}

}
