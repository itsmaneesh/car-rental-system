package com.cr.rentalsystem.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter @Getter
@ToString
public class Customer {
	
	private String firstName;
	private String lastName;
	private String dateOfBirth;

}
