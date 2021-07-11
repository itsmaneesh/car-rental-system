package com.cr.rentalsystem;

import com.cr.rentalsystem.booking.BookingDetail;
import com.cr.rentalsystem.constants.Constants;
import com.cr.rentalsystem.controller.RentalBookingController;
import com.cr.rentalsystem.customer.Customer;
import com.cr.rentalsystem.pojo.BookCar;
import com.cr.rentalsystem.pojo.ResponseMessage;
import com.cr.rentalsystem.pojo.VehicleDetail;
import com.cr.rentalsystem.pojo.VehicleToBook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarRentalSystemApplicationTests {

	@InjectMocks
	private RentalBookingController controller;

	@Test
	public void contextLoads() {
	}

	/**
	 *  Test scenarios to cover:
	 *  Vehicles inventory:
	 *  1. Get the list of available vehicles
	 *
	 *  Vehicle Booking:
	 *	1. Book a valid vehicle - Expected SUCCESS
	 *	2. Book an invalid vehicle - Expected FAILURE
	 * 	3. Book more than one vehicle - Expected SUCCESS
	 * 	4. Book mix of invalid and valid vehicles - Expected FAILURE
	 * 	5. Book a vehicle with a past date - Expected FAILURE
	 * 	6. Book a vehicle with invalid date - Expected FAILURE
	 * 	7. Book a vehicle with invalid from time - Expected FAILURE
	 * 	8. Book a vehicle without Customer names - Expected FAILURE
	 * 	9. Book a vehicle with invalid Customer D.O.B - Expected FAILURE
	 *
	 * 	Get Booking Details
	 *	1. Get the booking details of a vehicle that has been booked - Expected SUCCESS
	 *	2. Get the booking details of a vehicle that has NOT been booked - Expected FAILURE
	 *
	 */

	/**
	 * Test scenario: Vehicles inventory:	1. Get the list of available vehicles
	 */
	@Test
	public void getVehicleInventory_success() {
		ResponseEntity<List<VehicleDetail>> responseEntity = controller.getAvailableVehicle();
		assertNotNull(responseEntity);
	}

	/**
	 * Test scenario: Vehicle Booking: 1. Book a valid vehicle - Expected SUCCESS
	 */
	@Test
	public void bookValidVehicle_success() {
		ResponseMessage responseMessage = controller.bookACar(getBookCarStub(true, 1));
		System.out.println("Response: " + responseMessage);
		assertNotNull(responseMessage);
		assertFalse(responseMessage.isError());
	}

	/**
	 * Test scenario: Vehicle Booking: 2. Book an invalid vehicle - Expected FAILURE
	 */
	@Test
	public void bookInvalidVehicle_failure() {
		ResponseMessage responseMessage = controller.bookACar(getInvalidBookCarStub("Some vehicle"));
		System.out.println("Response: " + responseMessage);
		assertNotNull(responseMessage);
		assertTrue(responseMessage.isError());
	}

	/**
	 * Test scenario: Vehicle Booking: 3. Book more than one vehicle - Expected SUCCESS
	 */
	@Test
	public void bookMoreThanOneVehicle_success() {
		ResponseMessage responseMessage = controller.bookACar(getBookCarStub(true, 5));
		System.out.println("Response: " + responseMessage);
		assertNotNull(responseMessage);
		assertFalse(responseMessage.isError());
	}


	/**
	 * Test scenario: Vehicle Booking: 4. Book mix of invalid and valid vehicle - Expected FAILURE
	 */
	@Test
	public void bookMixOfInvalidAndValidVehicle_success() {
		BookCar bookCar = getBookCarStub(true, 8);
		bookCar.getVehicleToBook().add(getVehicleToBookStub(false));
		bookCar.getVehicleToBook().add(getVehicleToBookStub(false));
		ResponseMessage responseMessage = controller.bookACar(bookCar);
		System.out.println("Response: " + responseMessage);
		assertNotNull(responseMessage);
		assertTrue(responseMessage.isError());
	}

	/**
	 * Test scenario: Vehicle Booking: 5. Book a vehicle with a past date - Expected FAILURE
	 */
	@Test
	public void bookVehicleWithPastDate_failure() {
		ResponseMessage responseMessage = controller.bookACar(getBookCarStub(false, 1));
		System.out.println("Response: " + responseMessage);
		assertNotNull(responseMessage);
		assertTrue(responseMessage.isError());
	}

	/**
	 * Test scenario: Vehicle Booking: 6. Book a vehicle with invalid date - Expected FAILURE
	 */
	@Test
	public void bookVehicleWithInvalidDate_failure() {
		BookCar bookCar = getBookCarStub(true,1);
		bookCar.getVehicleToBook().stream().findFirst().get().setFromDate("20-20-20");
		ResponseMessage responseMessage = controller.bookACar(bookCar);
		System.out.println("Response: " + responseMessage);
		assertNotNull(responseMessage);
		assertTrue(responseMessage.isError());
		assertTrue(responseMessage.getErrorMessage().contains("Invalid from Date"));
	}


	/**
	 * Test scenario: Vehicle Booking: 7. Book a vehicle with invalid from time - Expected FAILURE
	 */
	@Test
	public void bookVehicleWithInvalidFromTime_failure() {
		BookCar bookCar = getBookCarStub(true,1);
		bookCar.getVehicleToBook().stream().findFirst().get().setFromTime("23:80:00");
		ResponseMessage responseMessage = controller.bookACar(bookCar);
		System.out.println("Response: " + responseMessage);
		assertNotNull(responseMessage);
		assertTrue(responseMessage.isError());
		assertTrue(responseMessage.getErrorMessage().contains("Invalid from time"));
	}

	/**
	 * Test scenario: Vehicle Booking: 8. Book a vehicle without Customer names - Expected FAILURE
	 */
	@Test
	public void bookVehicle_without_Customer_Names_failure() {
		BookCar bookCar = getBookCarStub(true, 1);
		bookCar.getCustomer().setFirstName("");
		bookCar.getCustomer().setLastName("");
		ResponseMessage responseMessage = controller.bookACar(bookCar);
		System.out.println("Response: " + responseMessage);
		assertNotNull(responseMessage);
		assertTrue(responseMessage.isError());
		assertTrue(responseMessage.getErrorMessage().contains("name is required"));
	}

	/**
	 * Test scenario: Vehicle Booking: 9. Book a vehicle with invalid Customer D.O.B - Expected FAILURE
	 */
	@Test
	public void bookVehicle_with_invalid_Customer_DOB_failure() {
		BookCar bookCar = getBookCarStub(true, 1);
		bookCar.getCustomer().setDateOfBirth("20-20-20");
		ResponseMessage responseMessage = controller.bookACar(bookCar);
		System.out.println("Response: " + responseMessage);
		assertNotNull(responseMessage);
		assertTrue(responseMessage.isError());
		assertTrue(responseMessage.getErrorMessage().contains("Invalid customer date of birth"));
	}


	/**
	 * Test scenario: Get Booking Details:	1. Get the booking details of a vehicle that has been booked - Expected SUCCESS
	 *
	 */
	@Test
	public void getBookingDetails_success() {
		int noOfVehicles = 1;
		ResponseMessage responseMessage = controller.bookACar(getBookCarStub(true, noOfVehicles));
		System.out.println("Response: "+responseMessage);
		assertNotNull(responseMessage);
		List<VehicleToBook> vehicleToBookList = (List<VehicleToBook>) responseMessage.getData();
		Object bookingDetailsObject = controller.getBookingByReferenceNumber(vehicleToBookList.stream().findFirst().get().getBookingReferenceNumber());
		System.out.println("Booking Details: "+ bookingDetailsObject);
		assertNotNull(vehicleToBookList);
		assertFalse(vehicleToBookList.isEmpty());
		assertEquals(vehicleToBookList.size(), noOfVehicles);
		assertNotNull(vehicleToBookList.stream().findFirst().get().getBookingReferenceNumber());
		assertNotNull(bookingDetailsObject);
		List<BookingDetail> alreadyBookedVehiclesList = (List<BookingDetail>) bookingDetailsObject;
		assertTrue(Objects.equals(vehicleToBookList.stream().findFirst().get().getBookingReferenceNumber(),
				alreadyBookedVehiclesList.get(0).getBookingReferenceNumber()));


	}

	/**
	 * Test scenario: Get Booking Details:	2. Get the booking details of a vehicle that has NOT been booked - Expected FAILURE
	 *
	 */
	@Test
	public void getBookingDetails_failure() {
		Object responseObject = controller.getBookingByReferenceNumber("11111");
		System.out.println("Response Object: "+responseObject);
		assertNotNull(responseObject);
		List<BookingDetail> bookedVehiclesList = (List<BookingDetail>) responseObject;
		assertTrue(CollectionUtils.isEmpty(bookedVehiclesList));
	}


	private BookCar getBookCarStub(boolean forSuccessScenario, int noOfVehicles) {
		BookCar bookCar = new BookCar();
		bookCar.setCustomer(getCustomerStub());
		List<VehicleToBook> vehicleToBookList = new ArrayList<>();
		for(int count =0; count < noOfVehicles; count++) {
			vehicleToBookList.add(getVehicleToBookStub(forSuccessScenario));
		}
		bookCar.setVehicleToBook(vehicleToBookList);
		return bookCar;
	}

	private Customer getCustomerStub() {
		return new Customer("Tony", "Stark", "1984-01-01");
	}

	private VehicleToBook getVehicleToBookStub(boolean forSuccess) {
		VehicleToBook vehicleToBook = new VehicleToBook();
		vehicleToBook.setVehicleType(Constants.SUV);
		if(forSuccess) {
			vehicleToBook.setFromDate("2021-07-22");
		}else {
			vehicleToBook.setFromDate("2020-07-22");
		}
		vehicleToBook.setFromTime("18:00:00");
		vehicleToBook.setNoOfDays("1");
		//vehicleToBook.setBookingReferenceNumber(UUID.randomUUID().toString());
		return vehicleToBook;
	}

	private BookCar getInvalidBookCarStub(String vehicleType) {
		BookCar bookCar = new BookCar();
		bookCar.setCustomer(getCustomerStub());
		List<VehicleToBook> vehicleToBookList = new ArrayList<>();
		vehicleToBookList.add(getInvalidVehicleToBookStub(vehicleType));
		bookCar.setVehicleToBook(vehicleToBookList);
		return bookCar;
	}

	private VehicleToBook getInvalidVehicleToBookStub(String vehicleType) {
		VehicleToBook vehicleToBook = new VehicleToBook();
		vehicleToBook.setVehicleType(vehicleType);
		vehicleToBook.setFromDate("2021-07-22");
		vehicleToBook.setFromTime("18:00:00");
		vehicleToBook.setNoOfDays("1");
		vehicleToBook.setBookingReferenceNumber(UUID.randomUUID().toString());
		return vehicleToBook;
	}

}
