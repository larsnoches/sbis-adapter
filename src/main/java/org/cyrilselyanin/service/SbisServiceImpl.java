package org.cyrilselyanin.service;

import lombok.Getter;
import org.cyrilselyanin.domain.Ticket;
import org.cyrilselyanin.dto.RegCashRequestDto;
import org.cyrilselyanin.dto.TokenRequestDto;
import org.cyrilselyanin.dto.TokenResponseDto;

import java.io.IOException;
import java.math.BigDecimal;

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

    public class RegCashRequestDtoAdapter {
        public RegCashRequestDto adapt(Ticket ticket) {
            RegCashRequestDto dto = new RegCashRequestDto();
            dto.setCompanyID(1L);
            dto.setCashierFIO("Петр Петрович Петров");
            dto.setOperationType(1);
            dto.setCashSum(null);
            dto.setBankSum(ticket.getPrice());
            dto.setInternetSum(ticket.getPrice());
            dto.setAccountSum(ticket.getPrice());
            dto.setPostpaySum(null);
            dto.setPrepaySum(ticket.getPrice());

            var vat20 = ticket.getPrice()
                    .multiply(BigDecimal.valueOf(20))
                    .divide(BigDecimal.valueOf(100));
            dto.setVatNone(ticket.getPrice().subtract(vat20));
            dto.setVatSum0(null);
            dto.setVatSum10(null);
            dto.setVatSum20(vat20);
            dto.setAllowRetailPayed(1);
            dto.setVatSum120(null);

            dto.setNameNomenclature(String.format(
                    "Поездка по маршруту %s, %s - %s, %t",
                    ticket.getBusRouteNumber(),
                    ticket.getDepartureBuspointName(),
                    ticket.getDepartureBuspointName(),
                    ticket.getDepartureDateTime()
            ));


            //

            return dto;
        }
    }
}
