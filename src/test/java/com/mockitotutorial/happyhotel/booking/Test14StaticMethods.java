package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mockStatic;

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
	void should_CalculateCorrectPrice() {
		try (MockedStatic<CurrencyConverter> mockedConverter = mockStatic(CurrencyConverter.class)) {
			// given
			BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5),
					2, false);
			double expected = 400.0;
			mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble())).thenReturn(400.0);

			// when
			double actual = bookingService.calculatePriceEuro(bookingRequest);
			// then
			assertEquals(expected, actual);
		}

	}
}
