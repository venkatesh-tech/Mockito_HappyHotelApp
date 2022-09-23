package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.*;
//import org.mockito.Mock;

class Test02DefaultReturnValues {

	// Main Class
	private BookingService bookingService;

	// dependencies [Four classes]
	private PaymentService paymentServiceMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private MailSender mailSenderMock;

	@BeforeEach
	void setup() {

		this.paymentServiceMock = mock(PaymentService.class); // gives a dummy payment service
		this.roomServiceMock = mock(RoomService.class); // @Mock can also be used
		this.bookingDAOMock = mock(BookingDAO.class);
		this.mailSenderMock = mock(MailSender.class);

		bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);

		// nice mocks returned values
		System.out.println("List Returned " + roomServiceMock.getAvailableRooms()); // 1. empty list
		System.out.println("Objects returned " + roomServiceMock.findAvailableRoomId(null)); // 2. null object
		System.out.println("primitive returned " + roomServiceMock.getRoomCount()); // 3. false primitives
	}

	@Test
	void should_CountAvaliablePlaces() {
		// given
		int expected = 0; // nice mocks will return a empty list because getAvailablePlaceCount will take
							// getAvailableRooms and getAvailableRooms will return empty list

		// when

		int actual = bookingService.getAvailablePlaceCount();

		// then
		assertEquals(expected, actual);
	}

}
