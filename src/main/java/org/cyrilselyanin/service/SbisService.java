package org.cyrilselyanin.service;

import org.cyrilselyanin.domain.Ticket;
import org.cyrilselyanin.dto.TokenRequestDto;
import java.io.IOException;

public interface SbisService {
    void requestToken(TokenRequestDto requestDto) throws IOException;
    void regCash(Ticket ticket);
}
