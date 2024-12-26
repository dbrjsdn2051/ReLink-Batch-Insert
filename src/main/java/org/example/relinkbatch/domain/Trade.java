package org.example.relinkbatch.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.relinkbatch.domain.enums.TradeCancelReason;
import org.example.relinkbatch.domain.enums.TradeStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trade extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_exchange_item_id")
    private ExchangeItem ownerExchangeItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_exchange_item_id")
    private ExchangeItem requesterExchangeItme;

    @Enumerated(EnumType.STRING)
    private TradeStatus tradeStatus = TradeStatus.AVAILABLE;

    private String ownerTrackingNumber;
    private String requesterTrackingNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(column = @Column(name = "owner_base_address"), name = "baseAddress"),
            @AttributeOverride(column = @Column(name = "owner_detail_address"), name = "detailAddress"),
            @AttributeOverride(column = @Column(name = "owner_zipcode"), name = "zipcode")
    })
    private Address ownerAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(column = @Column(name = "requester_base_address"), name = "baseAddress"),
            @AttributeOverride(column = @Column(name = "requester_detail_address"), name = "detailAddress"),
            @AttributeOverride(column = @Column(name = "requester_zipcode"), name = "zipcode")
    })
    private Address requesterAddress;

    private Boolean hasOwnerReceived = false;
    private Boolean hasRequesterReceived = false;
    private Boolean hasOwnerRequested = false;
    private Boolean hasRequesterRequested = false;
    private TradeCancelReason cancelReason;

    @Lob
    private String tradeCancelDescription;

    @Builder
    public Trade(
            Long id,
            User requester,
            ExchangeItem ownerExchangeItem,
            ExchangeItem requesterExchangeItme,
            TradeStatus tradeStatus,
            String ownerTrackingNumber,
            String requesterTrackingNumber,
            Address ownerAddress,
            Address requesterAddress,
            Boolean hasOwnerReceived,
            Boolean hasRequesterReceived,
            Boolean hasOwnerRequested,
            Boolean hasRequesterRequested,
            TradeCancelReason cancelReason,
            String tradeCancelDescription
    ) {
        this.id = id;
        this.requester = requester;
        this.ownerExchangeItem = ownerExchangeItem;
        this.requesterExchangeItme = requesterExchangeItme;
        this.tradeStatus = tradeStatus;
        this.ownerTrackingNumber = ownerTrackingNumber;
        this.requesterTrackingNumber = requesterTrackingNumber;
        this.ownerAddress = ownerAddress;
        this.requesterAddress = requesterAddress;
        this.hasOwnerReceived = hasOwnerReceived;
        this.hasRequesterReceived = hasRequesterReceived;
        this.hasOwnerRequested = hasOwnerRequested;
        this.hasRequesterRequested = hasRequesterRequested;
        this.cancelReason = cancelReason;
        this.tradeCancelDescription = tradeCancelDescription;
    }
}
