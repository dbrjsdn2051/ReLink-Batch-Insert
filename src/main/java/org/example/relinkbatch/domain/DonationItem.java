package org.example.relinkbatch.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.relinkbatch.domain.enums.DisposalType;
import org.example.relinkbatch.domain.enums.DonationStatus;
import org.example.relinkbatch.domain.enums.ItemQuality;
import org.example.relinkbatch.domain.enums.RejectedReason;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DonationItem extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String desiredDestination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category cateGory;

    @Lob
    private String detailRejectedReason;

    private String certificateUrl;

    private String destination;

    private String size;

    @Enumerated(EnumType.STRING)
    private Address returnAddress;

    @Enumerated(EnumType.STRING)
    private ItemQuality itemQuality;

    @Enumerated(EnumType.STRING)
    private DonationStatus donationStatus;

    @Enumerated(EnumType.STRING)
    private RejectedReason rejectedReason;

    @Enumerated(EnumType.STRING)
    private DisposalType disposalType;

    @Builder
    public DonationItem(
            Long id,
            String name,
            String description,
            String desiredDestination,
            User user,
            Category cateGory,
            String detailRejectedReason,
            String certificateUrl,
            String destination,
            String size,
            Address returnAddress,
            ItemQuality itemQuality,
            DonationStatus donationStatus,
            RejectedReason rejectedReason,
            DisposalType disposalType
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.desiredDestination = desiredDestination;
        this.user = user;
        this.cateGory = cateGory;
        this.detailRejectedReason = detailRejectedReason;
        this.certificateUrl = certificateUrl;
        this.destination = destination;
        this.size = size;
        this.returnAddress = returnAddress;
        this.itemQuality = itemQuality;
        this.donationStatus = donationStatus;
        this.rejectedReason = rejectedReason;
        this.disposalType = disposalType;
    }
}
