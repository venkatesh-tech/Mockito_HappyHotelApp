package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;

class Test10ArgumentCaptors {

	// Main Class
	private BookingService bookingService;

	// dependencies [Four classes]
	private PaymentService paymentServiceMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private MailSender mailSenderMock;
	private ArgumentCaptor<Double> doubleCaptor; // ArgumentCaptor catches double argument values;
	private ArgumentCaptor<BookingRequest> bookingRequestCaptor;

	@BeforeEach
	void setup() {

		this.paymentServiceMock = mock(PaymentService.class); // gives a dummy payment service
		this.roomServiceMock = mock(RoomService.class); // @Mock can also be used like
		this.bookingDAOMock = mock(BookingDAO.class);
		this.mailSenderMock = mock(MailSender.class);

		bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);

		this.doubleCaptor = ArgumentCaptor.forClass(Double.class); // Instance Created
	}

	@Test
	void should_PayCorrectPrice_When_InputOk() {

		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5), 2,
				true);

		// when

		bookingService.makeBooking(bookingRequest);

		// then
		verify(paymentServiceMock, times(1)).pay(eq(bookingRequest), doubleCaptor.capture());
		double capturedArgument = doubleCaptor.getValue();
//		System.out.println(capturedArgument);
		assertEquals(400.0, capturedArgument);
	}

	@Test
	void should_PayCorrectPrices_When_MultipleCalls() {

		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5), 2,
				true);
		BookingRequest bookingRequest2 = new BookingRequest("1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), 2,
				true);
		List<Double> expectedValues = Arrays.asList(400.0, 100.0);

		// when

		bookingService.makeBooking(bookingRequest);
		bookingService.makeBooking(bookingRequest2);

		// then
		verify(paymentServiceMock, times(2)).pay(any(), doubleCaptor.capture());
		List<Double> capturedArgument = doubleCaptor.getAllValues();

//		System.out.println(capturedArgument);
		assertEquals(expectedValues, capturedArgument);
	}

}
