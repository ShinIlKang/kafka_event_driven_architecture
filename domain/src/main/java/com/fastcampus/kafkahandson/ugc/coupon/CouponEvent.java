package com.fastcampus.kafkahandson.ugc.coupon;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 쿠폰 이벤트 관련 정보가 있는 클래스
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CouponEvent {
    private Long id; // coupon event의 id
    private String displayName; // coupon 대한 노출 이름
    private LocalDateTime expiresAt; // coupon의 만료 일시
    private Long issueLimit; // coupon 발급 제한 수

    @JsonIgnore
    public boolean isExpired() {
        return this.expiresAt.isBefore(LocalDateTime.now());
    }
}
