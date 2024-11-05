package store.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
    Promotions promotions = new Promotions();
    Products products = new Products(promotions);

    @Test
    void 결제_가능_테스트() {
        String name = "물";
        int howMuchBought = 2;

        Product water = products.getProduct(name);
        assertEquals(true, water.isSellable(howMuchBought));
    }

    @Test
    void 결제_불가능_테스트() {
        String name = "물";

        int howMuchBought = 11;

        Product water = products.getProduct(name);

        assertEquals(false, water.isSellable(howMuchBought));
    }

    @Test
    void 구매_테스트() {
        String name = "물";

        int howMuchBought = 2;
        Product water = products.getProduct(name);

        water.selled(howMuchBought);

        assertEquals(8, water.getQuantity());
    }

}
