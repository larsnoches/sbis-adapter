package org.cyrilselyanin.service;

import org.cyrilselyanin.domain.Ticket;
import org.cyrilselyanin.dto.TokenRequestDto;
import org.cyrilselyanin.exception.RegCashException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

@ExtendWith(MockitoExtension.class)
class SbisServiceTest {
    SbisServiceImpl sbisService;
    String token;
    String sid;

    @BeforeEach
    public void init() {
        sbisService = new SbisServiceImpl();

        TokenRequestDto requestDto = TokenRequestDto.create(
                "appClientId_123",
                "appSecret_123",
                "secretKey_123"
        );

        try {
            sbisService.requestToken(requestDto);
            token = sbisService.getToken();
            sid = sbisService.getSid();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Test
    public void getToken_thenCorrect() {
        assertNotNull(token);
        assertNotNull(sid);
        assertThat(token, instanceOf(String.class));
        assertThat(sid, instanceOf(String.class));
    }

    @Test
    public void regCash_theCorrectException() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setArrivalBuspointName("Уфа");
        ticket.setArrivalDatetime(
                Timestamp.valueOf(
                        LocalDateTime.of(2022, 06, 01, 10, 10)
                )
        );
        ticket.setDepartureDateTime(
                Timestamp.valueOf(
                        LocalDateTime.of(2022, 06, 01, 05, 40)
                )
        );
        ticket.setDepartureBuspointName("Тюмень");
        ticket.setIssueDateTime(
                Timestamp.valueOf(
                        LocalDateTime.of(2022, 05, 15, 8, 00)
                )
        );

        ticket.setPassengerFirstname("Олег");
        ticket.setPassengerMiddlename("Иванович");
        ticket.setPassengerLastname("Иванов");
        ticket.setPrice(BigDecimal.valueOf(550L));
        ticket.setQrCode("123-123-123");
        ticket.setCarrierName("МУП АТП");
        ticket.setBusRouteNumber("1010");

        assertThrows(RegCashException.class, () -> sbisService.regCash(ticket));
    }
}