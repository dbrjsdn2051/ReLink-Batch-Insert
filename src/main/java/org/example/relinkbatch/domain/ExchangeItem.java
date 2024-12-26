package org.example.relinkbatch.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.relinkbatch.domain.enums.ItemQuality;
import org.example.relinkbatch.domain.enums.TradeStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String name;

    @Lob
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category cateGory;

    @Enumerated(EnumType.STRING)
    private ItemQuality itemQuality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer deposit;

    private String size;
    private String brand;

    private String desiredItem;

    @Enumerated(EnumType.STRING)
    private TradeStatus tradeStatus;

    private Boolean isDeleted = false;

    @Builder
    public ExchangeItem(
            Long id,
            String name,
            String description,
            Category cateGory,
            ItemQuality itemQuality,
            User user,
            Integer deposit,
            String size,
            String brand,
            String desiredItem,
            TradeStatus tradeStatus,
            Boolean isDeleted
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cateGory = cateGory;
        this.itemQuality = itemQuality;
        this.user = user;
        this.deposit = deposit;
        this.size = size;
        this.brand = brand;
        this.desiredItem = desiredItem;
        this.tradeStatus = tradeStatus;
        this.isDeleted = isDeleted;
    }
}
