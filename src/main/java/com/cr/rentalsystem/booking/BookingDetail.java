package com.cr.rentalsystem.booking;

import java.time.LocalDateTime;
import com.cr.rentalsystem.customer.Customer;
import lombok.*;

@NoArgsConstructor
@Setter @Getter
@AllArgsConstructor
@ToString
public class BookingDetail {
	
	private Customer customer;
	private String vehicleType;
	private String vehicleSubType;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private String bookingReferenceNumber;
}
