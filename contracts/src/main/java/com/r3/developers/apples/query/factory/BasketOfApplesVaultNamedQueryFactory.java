package com.r3.developers.apples.query.factory;

import net.corda.v5.ledger.utxo.query.VaultNamedQueryFactory;
import net.corda.v5.ledger.utxo.query.registration.VaultNamedQueryBuilderFactory;
import org.jetbrains.annotations.NotNull;

public class BasketOfApplesVaultNamedQueryFactory implements VaultNamedQueryFactory {

    public final static String TOKEN_FARM_NAMED_QUERY = "TOKEN_FARM_NAMED_QUERY";

    public final static String TOKEN_OWNER_NAMED_QUERY = "TOKEN_OWNER_NAMED_QUERY";

    @Override
    public void create(@NotNull VaultNamedQueryBuilderFactory vaultNamedQueryBuilderFactory) {
        vaultNamedQueryBuilderFactory
                .create(TOKEN_FARM_NAMED_QUERY)
                .whereJson(
                        "WHERE visible_states.custom_representation -> 'com.r3.developers.apples.states.BasketOfApples' " +
                                "->> 'farm' = :farm"
                )
                .register();

        vaultNamedQueryBuilderFactory
                .create(TOKEN_OWNER_NAMED_QUERY)
                .whereJson(
                        "WHERE visible_states.custom_representation -> 'com.r3.developers.apples.states.BasketOfApples' " +
                                "->> 'owner' = :owner"
                )
                .register();

    }
}