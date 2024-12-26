package org.example.relinkbatch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.relinkbatch.domain.*;

@Getter
@AllArgsConstructor
public class MainCyclePair {
    private User requestUser;
    private User ownerUser;
    private Trade trade;
    private ExchangeItem requesterExchangeItem;
    private ExchangeItem ownerExchangeItem;
    private DonationItem ownerDonationItem;
    private Review requesterReview;
    private Review ownerReview;
    private Point requesterUserPoint;
    private Point ownerUserPoint;
}
