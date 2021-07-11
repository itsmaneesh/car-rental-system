
package com.cr.rentalsystem.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cr.rentalsystem.booking.BookingDetail;
import com.cr.rentalsystem.pojo.BookCar;
import com.cr.rentalsystem.pojo.ResponseMessage;
import com.cr.rentalsystem.pojo.VehicleDetail;
import com.cr.rentalsystem.pojo.VehicleToBook;
import com.cr.rentalsystem.util.RentalCarValidator;
import com.cr.rentalsystem.util.RentalUtil;

/**
 * @author maneesh.chintha
 *
 */
@RestController
public class RentalBookingController {
	
	@RequestMapping(value="/getAvailableCars", method=RequestMethod.GET, produces={"application/json"})
	public ResponseEntity<List<VehicleDetail>> getAvailableVehicle() {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(RentalUtil.getAvailableCars());
	}

	@RequestMapping(value="/book", method=RequestMethod.POST, consumes= {"application/json"}, produces={"application/json"})
	public ResponseMessage bookACar(@RequestBody BookCar carsToBook) {
		ResponseMessage respMessage = RentalCarValidator.validateInputCarDetails(carsToBook);
		if(!respMessage.isError()) {
			for(VehicleToBook vehicleToBook : carsToBook.getVehicleToBook()) {
				String referenceNumber = RentalUtil.bookCar(carsToBook.getCustomer(), vehicleToBook);
				if(null == referenceNumber) {
					vehicleToBook.setBookingReferenceNumber("null");
				}else {
					vehicleToBook.setBookingReferenceNumber(referenceNumber);
				}
			}
			respMessage.setData(carsToBook.getVehicleToBook());
		}
		
		return respMessage;
	}

	@RequestMapping(value="/getBookingDetail", method=RequestMethod.POST, produces={"application/json"})
	public Object getBookingByReferenceNumber(@RequestHeader("bookingReferenceNumbers") String bookingReferenceNumbers){
		List<BookingDetail>  bookingDetails = Collections.emptyList();
		if(null == bookingReferenceNumbers) {
			return bookingDetails;
		}
		bookingDetails = RentalUtil.getBookingByReferenceNumber(bookingReferenceNumbers);

		return bookingDetails;
	}

}
