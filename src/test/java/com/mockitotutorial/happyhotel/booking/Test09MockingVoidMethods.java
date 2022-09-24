package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
//import org.mockito.Mock;

class Test09MockingVoidMethods {

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
	void should_ThrowException_When_MailNotReady() {

		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5), 2,
				false);
		doThrow(new BusinessException()).when(mailSenderMock).sendBookingConfirmation(any());

		// when
		Executable executable = () -> bookingService.makeBooking(bookingRequest);

		// then
		assertThrows(BusinessException.class, executable);
	}

	@Test
	void should_NotThrowException_When_MailNotReady() {

		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5), 2,
				false);
		doNothing().when(mailSenderMock).sendBookingConfirmation(any());

		// when
		bookingService.makeBooking(bookingRequest);

		// then
		// No Exception Thrown
	}

}
