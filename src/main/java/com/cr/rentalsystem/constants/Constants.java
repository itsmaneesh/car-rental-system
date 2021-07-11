/**
 * 
 */
package com.cr.rentalsystem.constants;


/**
 * 
 * @author maneesh.chintha
*
 */
public class Constants {
	
	public static final String SEDAN = "SEDAN";
	
	public static final String SUV = "SUV";
	
	public static final String VAN = "VAN";
	
	public static final int SEDAN_VOLUME = 10;
	
	public static final int SUV_VOLUME = 10;
	
	public static final int VAN_VOLUME = 10;
	
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String TIME_FORMAT = "HH:MM:SS";
	
	public static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";

	public static final String REQUIRED_FIRST_NAME  = "customer first name is required !";
	
	public static final String REQUIRED_LAST_NAME  = "customer last name is required !";
	
	public static final String REQUIRED_DOB  = "customer date of birth is required !";
	
	public static final String INCORRECT_DOB  = "Invalid customer date of birth ! (format : yyyy-MM-dd)";
	
	public static final String INVALID_VEHICLE_TYPE = "Invalid vehicleType [%s]. Vehicle Type must be in (SEDAN, SUV, VAN) !";
	
	public static final String INVALID_EHICLE_REGISTER_FROM_DATE  = "Invalid from Date [%s] ! (format : yyyy-MM-dd)";
	
	public static final String INVALID_VEHICLE_REGISTER_FROM_TIME  = "Invalid from time [%s] ! (format : HH:mm:ss)";
	
	public static final String INVALID_BOOKING_DATE_TIME = "Invalid booking date [%s] and time [%s]. It should be after current date time.";
	
	public static final String INVALID_VEHICLE_QTY_REQ = "Total number of vehicle[type: %s, qty=%s] requested more than available qty[%s].";

}
