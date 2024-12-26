package org.example.relinkbatch.domain.enums;

import lombok.Getter;

@Getter
public enum TradeStatus {
    AVAILABLE,
    IN_EXCHANGE,
    EXCHANGED,
    CANCELED,
    IN_DELIVERY,
    UNAVAILABLE
}
