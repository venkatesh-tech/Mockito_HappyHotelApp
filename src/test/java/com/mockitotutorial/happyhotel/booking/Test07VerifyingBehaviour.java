package com.mockitotutorial.happyhotel.booking;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.*;
//import org.mockito.Mock;

class Test07VerifyingBehaviour {

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
		this.bookingDAOMock = mock(BookingDAO.class);
		this.mailSenderMock = mock(MailSender.class);

		bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
	}

	@Test
	void should_InvokePayemnt_When_Prepaid() {

		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5), 2,
				true);

		// when

		bookingService.makeBooking(bookingRequest);

		// then
		verify(paymentServiceMock, times(1)).pay(bookingRequest, 400.0); // checking method is called 1 time
		verifyNoMoreInteractions(paymentServiceMock); // Checking no more interactions after method called for 1 time

	}

	@Test
	void should_Not_InvokePayemnt_When_NotPrepaid() {

		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5), 2,
				false);

		// when

		bookingService.makeBooking(bookingRequest);

		// then
		verify(paymentServiceMock, never()).pay(any(), anyDouble()); // checking no method is called
	}

}
