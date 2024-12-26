package org.example.relinkbatch.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.relinkbatch.domain.enums.TradeReview;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal star;

    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_item_id")
    private ExchangeItem exchangeItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "review_trade",
            joinColumns = @JoinColumn(name = "review_id")
    )
    private List<TradeReview> tradeReview;

    @Builder
    public Review(Long id, BigDecimal star, String description, ExchangeItem exchangeItem, User writer, List<TradeReview> tradeReview) {
        this.id = id;
        this.star = star;
        this.description = description;
        this.exchangeItem = exchangeItem;
        this.writer = writer;
        this.tradeReview = tradeReview;
    }
}
