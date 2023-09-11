package com.r3.developers.apples.workflows;

import net.corda.v5.base.types.MemberX500Name;

import java.util.UUID;

public class RedeemApplesRequest {

    private MemberX500Name buyer;

    private UUID stampId;

    private Integer timeWindowsSize;


    // The JSON Marshalling Service, which handles serialisation, needs this constructor.
    private RedeemApplesRequest() {}

    private RedeemApplesRequest(MemberX500Name buyer, UUID stampId, Integer timeWindowsSize) {
        this.buyer = buyer;
        this.stampId = stampId;
        this.timeWindowsSize = timeWindowsSize;
    }

    public MemberX500Name getBuyer() {
        return buyer;
    }

    public UUID getStampId() {
        return stampId;
    }

    public Integer getTimeWindowsSize() {
        return timeWindowsSize;
    }
}