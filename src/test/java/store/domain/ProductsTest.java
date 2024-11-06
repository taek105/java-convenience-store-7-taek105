package store.domain;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ProductsTest {
    Products products = new Products(new Promotions());

    public ProductsTest() throws IOException {}

    @Test
    void 상품_찾기_테스트() {
        String name = "효택도시락";

        Product found = products.getProduct(name);

        System.out.println(found.getName() + "/" + found.getPromotion().getName());

        Product found2 = products.getProduct(name, false);

        System.out.println(found2.getName() + "/" + found2.getPromotion().getName());
    }
}
