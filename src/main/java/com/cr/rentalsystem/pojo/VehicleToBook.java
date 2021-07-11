package com.cr.rentalsystem.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter @Getter
@NoArgsConstructor
public class VehicleToBook {
	
	private String vehicleType;
	
	private String fromDate;
	
	private String fromTime;
	
	private String noOfDays;
	
	@JsonProperty(required=false)
	private String bookingReferenceNumber;
	
}
