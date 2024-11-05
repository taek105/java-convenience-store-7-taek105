package store;

import org.junit.jupiter.api.Test;
import store.domain.Promotions;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PromotionTest {
    Promotions promotions = new Promotions();

    @Test
    void 지금_프로모션_테스트1() {
        assertEquals(true, promotions.isPromotionNow("탄산2+1"));
    }
    @Test
    void 지금_프로모션_테스트2() {
        assertEquals(false, promotions.isPromotionNow("효택할인1"));
    }
    @Test
    void 지금_프로모션_테스트3() {
        assertEquals(false, promotions.isPromotionNow("효택할인2"));
    }
}
