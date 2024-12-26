package org.example.relinkbatch.domain.enums;

import lombok.Getter;

@Getter
public enum RejectedReason {
    QUALITY_STANDARD_FAILED,
    DAMAGED,
    RESTRICTED,
    SAFETY_HAZARD,
    INCOMPLETE,
    DIMENSION_LIMIT_EXCEEDED
}
