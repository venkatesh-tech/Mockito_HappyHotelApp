package com.mockitotutorial.happyhotel.booking;

import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.*;
//import org.mockito.Mock;

class Test08Spies {

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
		this.roomServiceMock = mock(RoomService.class); // @Mock can also be used like
		this.bookingDAOMock = spy(BookingDAO.class);
		this.mailSenderMock = mock(MailSender.class);

		bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
	}

	@Test
	void should_MakeBooking_When_InputOk() {

		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5), 2,
				true);

		// when
		String bookingId = bookingService.makeBooking(bookingRequest);
		verify(bookingDAOMock).save(bookingRequest);
		System.out.println("bookingId " + bookingId);

		// then

	}

	@Test
	void should_CancelBooking_When_InputOk() {

		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5), 2,
				true);
		bookingRequest.setRoomId("1.3");
		String bookingId = "1";

		doReturn(bookingRequest).when(bookingDAOMock).get(bookingId);

		// when
		bookingService.cancelBooking(bookingId);

		// then

	}

}
