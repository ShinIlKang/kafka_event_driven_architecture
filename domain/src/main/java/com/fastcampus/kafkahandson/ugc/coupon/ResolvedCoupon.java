package com.fastcampus.kafkahandson.ugc.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 쿠폰 화면 노출용 정보가 있는 클래스
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResolvedCoupon {
    private Coupon coupon;
    private CouponEvent couponEvent;

    public boolean canBeUsed() {
        return !this.couponEvent.isExpired() && this.coupon.getUsedAt() == null;
    }

    public static ResolvedCoupon generate(
            Coupon coupon,
            CouponEvent couponEvent
    ) {
        return new ResolvedCoupon(coupon, couponEvent);
    }
}
