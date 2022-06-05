package org.cyrilselyanin.service;

import org.cyrilselyanin.dto.TokenRequestDto;
import org.cyrilselyanin.exception.RegCashException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

@ExtendWith(MockitoExtension.class)
class SbisServiceTest {
    SbisServiceImpl sbisService;
    String token;
    String sid;

    @BeforeAll
    public void init() {
        sbisService = new SbisServiceImpl();

        TokenRequestDto requestDto = TokenRequestDto.create(
                "appClientId_123",
                "appSecret_123",
                "secretKey_123"
        );

        try {
            sbisService.getToken(requestDto);
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
    public void regCash_thenThrowsException() {
        var ticket = new Ticket();

        assertThrows(RegCashException.class, sbisService.regCash(token, sid, ticket));
    }
}