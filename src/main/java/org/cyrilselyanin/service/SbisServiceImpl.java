package org.cyrilselyanin.service;

import lombok.Getter;
import org.cyrilselyanin.domain.Ticket;
import org.cyrilselyanin.dto.RegCashRequestDto;
import org.cyrilselyanin.dto.RegCashResponseDto;
import org.cyrilselyanin.dto.TokenRequestDto;
import org.cyrilselyanin.dto.TokenResponseDto;
import org.cyrilselyanin.exception.RegCashException;

import java.io.IOException;
import java.math.BigDecimal;

public class SbisServiceImpl implements SbisService {
    private final String authUrl = "https://online.sbis.ru/oauth/service";
    private final String regCashUrl = "https://api.sbis.ru/retail/sale/create";
    private final SbisAuthService sbisAuthService = new SbisAuthService();
    private final SbisRetailService sbisRetailService = new SbisRetailService();

    @Getter
    private String token;

    @Getter
    private String sid;

    @Override
    public void requestToken(TokenRequestDto requestDto) throws IOException {
        TokenResponseDto responseDto = sbisAuthService.getToken(authUrl, requestDto);
        TokenResponseDtoAdapter tokenResponseDtoAdapter = new TokenResponseDtoAdapter();
        tokenResponseDtoAdapter.adapt(responseDto);
    }

    @Override
    public void regCash(Ticket ticket) {
        RegCashRequestDtoAdapter regCashRequestDtoAdapter = new RegCashRequestDtoAdapter();
        RegCashRequestDto regCashRequestDto = regCashRequestDtoAdapter.adapt(ticket);
        try {
            RegCashResponseDto regCashResponseDto = sbisRetailService.regCash(
                    regCashUrl, token, sid, regCashRequestDto
            );
        } catch (IOException | NullPointerException ex) {
            throw new RegCashException(ex.getMessage());
        }

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
            dto.setCashierFIO("Wow wow wow");
            dto.setOperationType(1);
            dto.setCashSum(null);
            dto.setBankSum(ticket.getPrice());
            dto.setInternetSum(ticket.getPrice());
            dto.setAccountSum(ticket.getPrice());
            dto.setPostpaySum(null);
            dto.setPrepaySum(ticket.getPrice());

            var vat20 = ticket.getPrice().divide(BigDecimal.valueOf(100));
            dto.setVatNone(ticket.getPrice().subtract(vat20));
            dto.setVatSum0(null);
            dto.setVatSum10(null);
            dto.setVatSum20(vat20);

            dto.setNameNomenclature(String.format(
                    "Маршрут %s, %s - %s, %s",
                    ticket.getBusRouteNumber(),
                    ticket.getDepartureBuspointName(),
                    ticket.getDepartureBuspointName(),
                    ticket.getDepartureDateTime().toString()
            ));

            //

            dto.setBarcodeNomenclature(BigDecimal.valueOf(12345));
            dto.setPriceNomenclature(ticket.getPrice());
            dto.setQuantityNomenclature(1);
            dto.setMeasureNomenclature("шт");
            dto.setKindNomenclature("у");
            dto.setTotalPriceNomenclature(ticket.getPrice());
            dto.setTaxRateNomenclature(vat20);
            dto.setTotalVat(vat20);
            dto.setCustomerFIO(
                    String.format(
                            "%s %s %s",
                            ticket.getPassengerLastname(),
                            ticket.getPassengerFirstname(),
                            ticket.getPassengerMiddlename()
                    )
            );
            dto.setCustomerEmail(null);
            dto.setCustomerPhone(null);
            dto.setCustomerExtId(null);
            dto.setTaxSystem(4);
            dto.setSendEmail(null);
            dto.setSendPhone(null);
            dto.setPropName(null);
            dto.setPropVa(null);
            dto.setComment("");
            dto.setPayMethod(4);
            dto.setExternalId("123-123-123");

            return dto;

        }
    }
}
