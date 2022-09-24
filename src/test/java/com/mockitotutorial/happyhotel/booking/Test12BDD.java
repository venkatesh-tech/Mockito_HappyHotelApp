package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
//import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class Test12BDD {

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
	void should_CountAvailablePlaces_When_OneRoomAvailable() {

		// given
		given(this.roomServiceMock.getAvailableRooms()).willReturn(Collections.singletonList(new Room("Room 1", 2)));
		int expected = 2;
		// when
		int actual = bookingService.getAvailablePlaceCount();
		// then
		assertEquals(expected, actual);
	}

	@Test
	void should_InvokePayemnt_When_Prepaid() {

		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5), 2,
				true);

		// when

		bookingService.makeBooking(bookingRequest);

		// then
//		then(paymentServiceMock).should(times(1)).pay(bookingRequest, 400.0); // checking method is called 1 time
		verifyNoMoreInteractions(paymentServiceMock); // Checking no more interactions after method called for 1 time

	}
}
