package com.fastcampus.kafkahandson.ugc.port;

/**
 * 쿠폰 발급 요청 이력 조회
 *
 */
public interface CouponIssueRequestHistoryPort {
    // 해당 쿠폰 이벤트 내에서, 유저의 발급 요청이력이 없다면 기록
    boolean setHistoryIfnotExists(Long couponEventId, Long userId);

    // 해당 쿠폰 이벤트 내에서, 발급 요청을 몇번째로 했는지 확인
    Long getRequestSequentialNumber(Long couponEventId);
}
