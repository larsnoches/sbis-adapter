package org.cyrilselyanin.service;

import org.cyrilselyanin.dto.TokenRequestDto;
import java.io.IOException;

public interface SbisService {
    void getToken(TokenRequestDto requestDto) throws IOException;
}
