package com.fastcampus.kafkahandson.ugc;

public interface CouponIssueHistoryUsecase {
    /**
     * 첫 번째 요청인지 여부
     * 
     * @param couponEventId
     * @param userId
     * @return
     */
    boolean isFirstRequestFromUser(Long couponEventId, Long userId);

    /**
     * 남은 쿠폰이 있는지 여부
     *
     * @param couponEventId
     * @return
     */
    boolean hasRemainingCoupon(Long couponEventId);
}
