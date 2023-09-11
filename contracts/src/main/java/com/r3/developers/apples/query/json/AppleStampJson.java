package com.r3.developers.apples.query.json;

import org.jetbrains.annotations.NotNull;

public class AppleStampJson {

    private final String id;
    private final String stampDesc;
    private final String issuer;
    private final String holder;

    public AppleStampJson(String id, String stampDesc, String issuer, String holder) {
        this.id = id;
        this.stampDesc = stampDesc;
        this.issuer = issuer;
        this.holder = holder;
    }

    @NotNull
    public String getId() {
        return id;
    }

    @NotNull
    public String getStampDesc() {
        return stampDesc;
    }

    @NotNull
    public String getIssuer() {
        return issuer;
    }

    @NotNull
    public String getHolder() {
        return holder;
    }

}
