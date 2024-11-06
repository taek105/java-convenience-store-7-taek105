package store.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PromotionTest {
    Promotions promotions = new Promotions();

    @Test
    void 지금_프로모션_테스트1() {
        Promotion promotion = new Promotion(
                "탄산2+1",
                "2",
                "1",
                "2024-01-01",
                "2024-12-31"
        );

        assertEquals(true, promotion.isPromotionNow());
    }
    @Test
    void 지금_프로모션_테스트2() {
        Promotion promotion = new Promotion(
                "효택할인1",
                "3",
                "1",
                "2025-01-01",
                "2025-10-05"
        );

        assertEquals(false, promotion.isPromotionNow());
    }
    @Test
    void 지금_프로모션_테스트3() {
        Promotion promotion = new Promotion(
                "효택할인2",
                "4",
                "2",
                "2024-11-01",
                "2024-11-03"
        );

        assertEquals(false, promotion.isPromotionNow());
    }
}