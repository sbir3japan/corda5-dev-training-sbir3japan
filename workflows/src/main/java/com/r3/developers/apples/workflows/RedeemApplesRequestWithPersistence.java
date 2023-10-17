//package com.r3.developers.apples.workflows;
//
//import net.corda.v5.base.types.MemberX500Name;
//
//import java.util.UUID;
//
//public class RedeemApplesRequestWithPersistence {
//
//    private MemberX500Name buyer;
//
//    private UUID stampId;
//
//    private Integer timeWindowsSize;
//
//
//    // The JSON Marshalling Service, which handles serialisation, needs this constructor.
//    private RedeemApplesRequestWithPersistence() {}
//
//    private RedeemApplesRequestWithPersistence(MemberX500Name buyer, UUID stampId, Integer timeWindowsSize) {
//        this.buyer = buyer;
//        this.stampId = stampId;
//        this.timeWindowsSize = timeWindowsSize;
//    }
//
//    public MemberX500Name getBuyer() {
//        return buyer;
//    }
//
//    public UUID getStampId() {
//        return stampId;
//    }
//
//    public Integer getTimeWindowsSize() {
//        return timeWindowsSize;
//    }
//}
//
////package com.r3.developers.apples.workflows;
////
////import net.corda.v5.base.types.MemberX500Name;
////
////import java.util.UUID;
////
////public class RedeemApplesRequestWithPersistence {
////
////    private MemberX500Name buyer;
////
////    private UUID stampId;
////
////    // The JSON Marshalling Service, which handles serialisation, needs this constructor.
////    private RedeemApplesRequestWithPersistence() {}
////
////    private RedeemApplesRequestWithPersistence(MemberX500Name buyer, UUID stampId) {
////        this.buyer = buyer;
////        this.stampId = stampId;
////    }
////
////    public MemberX500Name getBuyer() {
////        return buyer;
////    }
////
////    public UUID getStampId() {
////        return stampId;
////    }
////}