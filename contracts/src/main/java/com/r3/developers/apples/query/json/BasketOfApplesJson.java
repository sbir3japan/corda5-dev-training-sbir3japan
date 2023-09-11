package com.r3.developers.apples.query.json;

import org.jetbrains.annotations.NotNull;

public class BasketOfApplesJson {

    private final String description;
    private final String farm;
    private final String owner;
    private final Integer weight;

    public BasketOfApplesJson(String description, String farm, String owner, Integer weight) {
        this.description = description;
        this.farm = farm;
        this.owner = owner;
        this.weight = weight;
    }


    public String getDescription() {
        return description;
    }

    public String getFarm() {
        return farm;
    }

    public String getOwner() {
        return owner;
    }

    public Integer getWeight() {
        return weight;
    }
}
