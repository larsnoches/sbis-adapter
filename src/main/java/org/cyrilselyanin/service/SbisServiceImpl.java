package org.cyrilselyanin.service;

import lombok.Getter;
import org.cyrilselyanin.dto.TokenRequestDto;
import org.cyrilselyanin.dto.TokenResponseDto;

import java.io.IOException;

public class SbisServiceImpl implements SbisService {
    private final String authUrl = "https://online.sbis.ru/oauth/service";
    private final SbisAuthService sbisAuthService = new SbisAuthService();

    @Getter
    private String token;

    @Getter
    private String sid;

    @Override
    public void getToken(TokenRequestDto requestDto) throws IOException {
        TokenResponseDto responseDto = sbisAuthService.getToken(authUrl, requestDto);
        TokenResponseDtoAdapter tokenResponseDtoAdapter = new TokenResponseDtoAdapter();
        tokenResponseDtoAdapter.adapt(responseDto);
    }

    public class TokenResponseDtoAdapter {
        public void adapt(TokenResponseDto dto) {
            token = dto.getToken();
            sid = dto.getSid();
        }
    }
}
