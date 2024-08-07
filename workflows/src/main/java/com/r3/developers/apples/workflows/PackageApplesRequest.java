package com.r3.developers.apples.workflows;

import net.corda.v5.base.types.MemberX500Name;

public class PackageApplesRequest {

    private String appleDescription;
    private int weight;

    // The JSON Marshalling Service, which handles serialisation, needs this constructor.
    public PackageApplesRequest() {}

    public PackageApplesRequest(String appleDescription, int weight) {
        this.appleDescription = appleDescription;
        this.weight = weight;
    }

    public String getAppleDescription() {
        return appleDescription;
    }

    public int getWeight() {
        return weight;
    }
}
