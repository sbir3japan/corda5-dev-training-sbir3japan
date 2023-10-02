//package com.r3.developers.apples.schema;
//
//import net.corda.v5.base.annotations.CordaSerializable;
//
//import javax.persistence.*;
//import java.time.Instant;
//import java.util.Objects;
//import java.util.UUID;
//
//@CordaSerializable
//@Entity(name = "apple")
//public class Apple {
//
//    @Id
//    @Column(name = "id")
//    private final UUID id;
//
//    @Column(name = "shipping_date")
//    private final Instant shippingDate;
//
//    @Column(name = "buyer")
//    private final String buyer;
//
//    @Column(name = "weight")
//    private final Integer weight;
//
//    public Apple(){
//        this.id =  null;
//        this.shippingDate = null;
//        this.buyer = null;
//        this.weight = null;
//    }
//
//    public Apple(UUID id, Instant shippingDate, String buyer, Integer weight) {
//        this.id = id;
//        this.shippingDate = shippingDate;
//        this.buyer = buyer;
//        this.weight = weight;
//    }
//
//    // Getter and Setter methods for id, name, birthdate, and owner go here
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Apple apple = (Apple) o;
//
//        if (!id.equals(apple.id)) return false;
//        if (!shippingDate.equals(apple.shippingDate)) return false;
//        if (!buyer.equals(apple.buyer)) return false;
//
//        return Objects.equals(weight, apple.weight);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = id.hashCode();
//        result = 31 * result + shippingDate.hashCode();
//        result = 31 * result + buyer.hashCode();
//        result = 31 * result + weight.hashCode();
//        return result;
//    }
//
//    public UUID getId() {
//        return id;
//    }
//
//    public Instant getShippingDate() {
//        return shippingDate;
//    }
//
//    public String getBuyer() {
//        return buyer;
//    }
//
//    public Integer getWeight() {
//        return weight;
//    }
//}
