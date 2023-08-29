package com.r3.developers.apples.workflows;

import com.r3.developers.apples.states.AppleStamp;
import net.corda.v5.application.flows.CordaInject;
import net.corda.v5.application.flows.InitiatedBy;
import net.corda.v5.application.flows.ResponderFlow;
import net.corda.v5.application.membership.MemberLookup;
import net.corda.v5.application.messaging.FlowSession;
import net.corda.v5.base.annotations.Suspendable;
import net.corda.v5.base.exceptions.CordaRuntimeException;
import net.corda.v5.ledger.utxo.UtxoLedgerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@InitiatedBy(protocol = "redeem-apples")
public class RedeemApplesResponderFlow implements ResponderFlow {

    private String WRONG_NODE_MESSAGE = "The Description must contain the Common Name of the Responder Node.";

    private final static Logger log = LoggerFactory.getLogger(RedeemApplesResponderFlow.class);

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
//            AppleStamp inputAppleStamp = _transaction.getInputStates(AppleStamp.class).get(0);
//            String stampDescOfBasket = inputAppleStamp.getStampDesc();
//
//            if (!stampDescOfBasket.contains(commonNameOfMyNode)) {
//                log.warn(WRONG_NODE_MESSAGE);
//                throw new CordaRuntimeException(WRONG_NODE_MESSAGE);
//            }
        });
    }
}