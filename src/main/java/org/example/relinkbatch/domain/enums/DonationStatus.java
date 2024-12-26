package org.example.relinkbatch.domain.enums;

import lombok.Getter;

@Getter
public enum DonationStatus {
    PENDING_REGISTRATION,
    REGISTRATION_COMPLETED,
    UNDER_INSPECTION,
    INSPECTION_COMPLETED,
    DONATION_COMPLETED,
    INSPECTION_REJECTED
}
