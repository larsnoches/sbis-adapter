package org.cyrilselyanin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ticket {
    // Идентификатор
    private Long id;

    // Дата и время оформления
    private Timestamp issueDateTime;

    // Фамилия пассажира
    private String passengerLastname;

    // Имя пассажира
    private String passengerFirstname;

    // Отчество пассажира
    private String passengerMiddlename;

    // Номер маршрута автобуса
    private String busRouteNumber;

    // QR-код билета
    private String qrCode;

    // Номер места
    private String seatName;

    // Наименование перевозчика
    private String carrierName;

    // Наименование пункта отправления
    private String departureBuspointName;

    // Наименование пункта назначения
    private String arrivalBuspointName;

    // Дата и время отправления
    private Timestamp departureDateTime;

    // Дата и время прибытия
    private Timestamp arrivalDatetime;

    // Стоимость
    private BigDecimal price;

//    private BusTrip busTrip;

    // Идентификатор пользователя
    private String userId;
}
