package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
//import org.mockito.Mock;

class Test06Matchers {

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
	void should_NotCompleteBooking_When_PriceTooHigh() {

		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5), 2,
				true);
//		when(this.paymentServiceMock.pay(any(), anyDouble())).thenThrow(BusinessException.class); //for primitives 
		when(this.paymentServiceMock.pay(any(), eq(400.0))).thenThrow(BusinessException.class); // for exact values use
																								// eq

		// when
		Executable executable = () -> bookingService.makeBooking(bookingRequest);

		// then
		assertThrows(BusinessException.class, executable);
	}

}
