package com.r3.developers.apples.query.factory;

import net.corda.v5.ledger.utxo.StateAndRef;
import net.corda.v5.ledger.utxo.query.VaultNamedQueryFactory;
import net.corda.v5.ledger.utxo.query.VaultNamedQueryStateAndRefFilter;
import net.corda.v5.ledger.utxo.query.registration.VaultNamedQueryBuilderFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class AppleStampVaultNamedQueryFactory implements VaultNamedQueryFactory {

    public final static String TOKEN_ISSUER_NAMED_QUERY = "TOKEN_ISSUER_NAMED_QUERY";

    public final static String TOKEN_HOLDER_NAMED_QUERY = "TOKEN_HOLDER_NAMED_QUERY";

    @Override
    public void create(@NotNull VaultNamedQueryBuilderFactory vaultNamedQueryBuilderFactory) {
        vaultNamedQueryBuilderFactory
                .create(TOKEN_ISSUER_NAMED_QUERY)
                .whereJson(
                        "WHERE visible_states.custom_representation -> 'com.r3.developers.apples.states.AppleStamp' " +
                                "->> 'issuer' = :issuer " +
                                "AND visible_states.consumed IS NULL"
                )
                .register();

        vaultNamedQueryBuilderFactory
                .create(TOKEN_HOLDER_NAMED_QUERY)
                .whereJson(
                        "WHERE visible_states.custom_representation -> 'com.r3.developers.apples.states.AppleStamp' " +
                                "->> 'holder' = :holder " +
                                "AND visible_states.consumed IS NULL"
                )
                .register();

    }
}