package com.fastcampus.kafkahandson.ugc.coupon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 발급 받은 쿠폰 관련 정보가 있는 클래스
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Coupon {
    private Long id; // coupon id
    private Long userId; // 사용자 id
    private Long couponEventId; // 생성한 coupon event id
    private LocalDateTime issuedAt; // 발급일시
    private LocalDateTime usedAt; // 사용일시

    public Coupon use() {
        this.usedAt = LocalDateTime.now();
        return this;
    }

    public static Coupon generate(
            Long userId,
            Long couponEventId
    ) {
        return new Coupon(null, userId, couponEventId, LocalDateTime.now(), null);
    }
}
