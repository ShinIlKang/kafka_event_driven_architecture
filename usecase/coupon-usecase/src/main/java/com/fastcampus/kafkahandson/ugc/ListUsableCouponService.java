package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.coupon.ResolvedCoupon;
import com.fastcampus.kafkahandson.ugc.port.CouponPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListUsableCouponService implements ListUsableCouponUsecase {
    private final CouponPort couponPort;

    /**
     * 사용할수 있는 쿠폰 목록
     * 
     * @param userId
     * @return
     */
    @Override
    public List<ResolvedCoupon> listByUserId(Long userId) {
        List<ResolvedCoupon> resolvedCoupons = couponPort.listByUserId(userId);

        return resolvedCoupons.stream().filter(ResolvedCoupon::canBeUsed).toList();
    }
}
