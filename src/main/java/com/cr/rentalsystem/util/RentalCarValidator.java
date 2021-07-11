/**
 * 
 */
package com.cr.rentalsystem.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.cr.rentalsystem.constants.Constants;
import com.cr.rentalsystem.customer.Customer;
import com.cr.rentalsystem.pojo.BookCar;
import com.cr.rentalsystem.pojo.ResponseMessage;
import com.cr.rentalsystem.pojo.VehicleDetail;
import com.cr.rentalsystem.pojo.VehicleToBook;
import org.springframework.util.StringUtils;

/**
 * @author maneesh.chintha
 *
 */
public class RentalCarValidator {
	
	public static ResponseMessage validateInputCarDetails(BookCar carsToBook) {
		
		Customer customer = carsToBook.getCustomer();
		//validate first name
		if(StringUtils.isEmpty(customer.getFirstName())) {
			return getResponseMessage(true, Constants.REQUIRED_FIRST_NAME, null);
		}
		
		//validate last name
		if(StringUtils.isEmpty(customer.getLastName())) {
			return getResponseMessage(true, Constants.REQUIRED_LAST_NAME, null);
		}
		
		//validate date of birth
		if(StringUtils.isEmpty(customer.getLastName())) {
			return getResponseMessage(true, Constants.REQUIRED_DOB, null);
		}
		
		if(!isDateValid(customer.getDateOfBirth(), Constants.DATE_FORMAT)) {
			return getResponseMessage(true, Constants.INCORRECT_DOB, null);
		}
		
		// validate each car
		for(VehicleToBook vehicleToBook : carsToBook.getVehicleToBook()) {
			
			//validate vehicle Type
			if(! (Constants.SEDAN.equalsIgnoreCase(vehicleToBook.getVehicleType()) ||
					Constants.SUV.equalsIgnoreCase(vehicleToBook.getVehicleType()) ||
					Constants.VAN.equalsIgnoreCase(vehicleToBook.getVehicleType()))) {
				return getResponseMessage(true, String.format(Constants.INVALID_VEHICLE_TYPE, vehicleToBook.getVehicleType()), null);
			}
			
			//validate from date
			if(!isDateValid(vehicleToBook.getFromDate(), Constants.DATE_FORMAT)) {
				return getResponseMessage(true, Constants.INVALID_EHICLE_REGISTER_FROM_DATE, null);
			}
			
			//validate from time
			if(!isValidTime(vehicleToBook.getFromTime(), Constants.TIME24HOURS_PATTERN)) {
				return getResponseMessage(true, String.format(Constants.INVALID_VEHICLE_REGISTER_FROM_TIME, vehicleToBook.getFromTime()), null);
			}
			
			// validate date time is greater than current time
			if(!isFromDateTimeValid(vehicleToBook.getFromDate(), vehicleToBook.getFromTime())) {
				return getResponseMessage(true, String.format(Constants.INVALID_BOOKING_DATE_TIME, vehicleToBook.getFromDate(), vehicleToBook.getFromTime()), null);
			}
	
		}
		
		//validate total number of vehicle requested vs available
		int totalSedanRequested = carsToBook.getVehicleToBook().stream().filter(p -> Constants.SEDAN.equalsIgnoreCase(p.getVehicleType())).collect(Collectors.toList()).size();
		int totalSuvRequested = carsToBook.getVehicleToBook().stream().filter(p -> Constants.SUV.equalsIgnoreCase(p.getVehicleType())).collect(Collectors.toList()).size();
		int totalVanRequested = carsToBook.getVehicleToBook().stream().filter(p -> Constants.VAN.equalsIgnoreCase(p.getVehicleType())).collect(Collectors.toList()).size();
		
		List<VehicleDetail> availableCars = RentalUtil.getAvailableCars();
		int availableSedan = 0;
		int availableSuv = 0;
		int availableVan = 0;
		
		for(VehicleDetail vehicleDetail : availableCars) {
			if(Constants.SEDAN.equalsIgnoreCase(vehicleDetail.getSubType())) {
				availableSedan = vehicleDetail.getAvailableQty();
			}
			if(Constants.SUV.equalsIgnoreCase(vehicleDetail.getSubType())) {
				availableSuv = vehicleDetail.getAvailableQty();
			}
			if(Constants.VAN.equalsIgnoreCase(vehicleDetail.getSubType())) {
				availableVan = vehicleDetail.getAvailableQty();
			}
		}
		
		if(totalSedanRequested != 0  && totalSedanRequested > availableSedan) {
			return getResponseMessage(true, String.format(Constants.INVALID_VEHICLE_QTY_REQ, Constants.SEDAN, totalSedanRequested, availableSedan), null);
		}
		
		if(totalSuvRequested != 0  && totalSuvRequested > availableSuv) {
			return getResponseMessage(true, String.format(Constants.INVALID_VEHICLE_QTY_REQ, Constants.SUV, totalSuvRequested, availableSuv), null);
		}
		
		if(totalVanRequested != 0  && totalVanRequested > availableVan) {
			return getResponseMessage(true, String.format(Constants.INVALID_VEHICLE_QTY_REQ, Constants.VAN, totalVanRequested, availableVan), null);
		}
		
		return getResponseMessage(false, null, carsToBook.getVehicleToBook());
	}
	
	/**
	 * 
	 * @param isError
	 * @param errorMessage
	 * @param data
	 * @return
	 */
	public static ResponseMessage getResponseMessage(boolean isError, String errorMessage, Object data) {
		ResponseMessage respMsg = new ResponseMessage();
		respMsg.setError(isError);
		respMsg.setErrorMessage(errorMessage);
		respMsg.setData(data);
		return respMsg;
	}
	
	/**
	 * 
	 * @param timeToValidate
	 * @param timePattern
	 * @return
	 */
	public static boolean isValidTime(String timeToValidate, String timePattern) {
		Pattern pattern = Pattern.compile(timePattern);
		Matcher matcher = pattern.matcher(timeToValidate);
		return matcher.matches();
	}
	
	/**
	 * 
	 * @param dateToValidate
	 * @param timeStr
	 * @return
	 */
	public static boolean isFromDateTimeValid(String dateToValidate,  String timeStr) {
		if (dateToValidate == null) {
			return false;
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT);
		try {
			String dateStr = dateToValidate.concat(" ").concat(timeStr);
			LocalDateTime enteredDateTime = LocalDateTime.parse(dateStr, dtf);
			LocalDateTime now = LocalDateTime.now();
			if(enteredDateTime.isBefore(now)){
				return false;
			}
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param dateToValidate
	 * @param dateFromat
	 * @return
	 */
	public static boolean isDateValid(String dateToValidate, String dateFromat) {

		if (dateToValidate == null) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		Date date = null;
		try {
			date = sdf.parse(dateToValidate);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}


}
