package store.domain;

import org.junit.jupiter.api.Test;
import store.view.OutputView;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
    Promotions promotions = new Promotions();
    Products products = new Products(promotions);

    public ProductTest() throws IOException {}

    @Test
    void print_Test() {
        OutputView.printProducts(products);
    }

    @Test
    void 결제_가능_테스트() {
        String name = "물";
        int amount = 2;

        Product water = products.getProduct(name);
        System.out.println(water.getName());
        assertEquals(true, water.isSellable(amount));
    }

    @Test
    void 결제_불가능_테스트() {
        String name = "물";

        int amount = 11;

        Product water = products.getProduct(name);

        assertEquals(false, water.isSellable(amount));
    }

    @Test
    void 구매_테스트() {
        String name = "물";

        int amount = 2;
        Product water = products.getProduct(name);

        water.sold(amount);

        assertEquals(8, water.getQuantity());
    }

}
