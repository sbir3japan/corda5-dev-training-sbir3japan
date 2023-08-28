package com.r3.developers.apples.workflows;

import com.r3.developers.apples.states.AppleStamp;
import com.r3.developers.apples.states.BasketOfApples;
import net.corda.v5.application.flows.CordaInject;
import net.corda.v5.application.flows.InitiatedBy;
import net.corda.v5.application.flows.ResponderFlow;
import net.corda.v5.application.membership.MemberLookup;
import net.corda.v5.application.messaging.FlowSession;
import net.corda.v5.base.annotations.Suspendable;
import net.corda.v5.base.exceptions.CordaRuntimeException;
import net.corda.v5.ledger.utxo.UtxoLedgerService;

import java.security.PublicKey;

@InitiatedBy(protocol = "redeem-apples")
public class RedeemApplesResponderFlow implements ResponderFlow {

    @CordaInject
    private UtxoLedgerService utxoLedgerService;

    @CordaInject
    private MemberLookup memberLookup;

    @Suspendable
    @Override
    public void call(FlowSession session) {
        // Receive, verify, validate, sign and record the transaction sent from the initiator
        utxoLedgerService.receiveFinality(session, _transaction -> {
            /*
             * [receiveFinality] will automatically verify the transaction and its signatures before signing it.
             * However, just because a transaction is contractually valid doesn't mean we necessarily want to sign.
             * What if we don't want to deal with the counterparty in question, or the value is too high,
             * or we're not happy with the transaction's structure? [UtxoTransactionValidator] (the lambda created
             * here) allows us to define the additional checks. If any of these conditions are not met,
             * we will not sign the transaction - even if the transaction and its signatures are contractually valid.
             */
//            String commonNameOfMyNode = memberLookup.myInfo().getName().getCommonName();
//            BasketOfApples outputBasketOfApples = _transaction.getOutputStates(BasketOfApples.class).get(0);
//            String descriptionOfBasket = outputBasketOfApples.getDescription();
//
//            if (!descriptionOfBasket.contains(commonNameOfMyNode)) {
//                throw new CordaRuntimeException("The Description must contain the Common Name of the Responder Node.");
//            }
        });
    }
}