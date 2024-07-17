package com.r3.developers.apples.workflows;

public class RedeemApplesResponse {
    private String transactionId;
    private String message;

    public RedeemApplesResponse() {}

    public RedeemApplesResponse(String transactionId, String myName, String buyerName) {
        this.transactionId = transactionId;
        this.message = myName + " traded BasketOfApples for " + buyerName + "'s AppleStamp. "
                + "And we checked that the owner of BasketOfApples and the issuer of AppleStamp are the same.";
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public String getMessage() {
        return this.message;
    }

}
