package com.mockitotutorial.happyhotel.booking;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
//import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class Test14StaticMethods {

	// Main Class
	@InjectMocks // It will inject mocks and spies
	private BookingService bookingService;

	// dependencies [Four classes]
	@Mock
	private PaymentService paymentServiceMock;

	@Mock
	private RoomService roomServiceMock;

	@Spy
	private BookingDAO bookingDAOMock;

	@Mock
	private MailSender mailSenderMock;
	@Captor
	private ArgumentCaptor<Double> doubleCaptor; // ArgumentCaptor catches double argument values;

	@Captor
	private ArgumentCaptor<BookingRequest> bookingRequestCaptor;

	@Test
	void should_InvokePayemnt_When_Prepaid() {

		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5), 2,
				false);
		lenient().when(paymentServiceMock.pay(any(), anyDouble())).thenReturn("1"); // Unecessary stubbing behaviour
		// Strict Stubbing

		// when

		bookingService.makeBooking(bookingRequest);

		// then
		// No exception is thrown
	}
}
