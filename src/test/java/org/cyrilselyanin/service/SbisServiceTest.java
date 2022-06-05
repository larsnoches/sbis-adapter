package org.cyrilselyanin.service;

import org.cyrilselyanin.dto.TokenRequestDto;
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

    @BeforeEach
    public void init() {
        sbisService = new SbisServiceImpl();
    }

    @Test
    public void getToken_thenCorrect() {
        TokenRequestDto requestDto = TokenRequestDto.create(
                "appClientId_123",
                "appSecret_123",
                "secretKey_123"
        );

        try {
            sbisService.getToken(requestDto);
            String token = sbisService.getToken();
            String sid = sbisService.getSid();
            assertNotNull(token);
            assertNotNull(sid);
            assertThat(token, instanceOf(String.class));
            assertThat(sid, instanceOf(String.class));
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}