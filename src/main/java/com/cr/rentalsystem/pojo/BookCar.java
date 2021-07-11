package com.cr.rentalsystem.pojo;

import java.util.List;
import com.cr.rentalsystem.customer.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class BookCar {
	
	@JsonProperty("customer")
	private Customer customer;
	
	@JsonProperty("vehicle")
	private List<VehicleToBook> VehicleToBook;

}
