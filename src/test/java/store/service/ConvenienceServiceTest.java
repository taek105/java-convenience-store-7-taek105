package store.service;

import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotions;
import store.model.PurchaseDTO;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConvenienceServiceTest {
    Products products = new Products(new Promotions());
    ConvenienceService convenienceService = new ConvenienceService();

    public ConvenienceServiceTest() throws IOException {}

    @Test
    void 투쁠투_4개구매_테스트() {
        String productName = "효택도시락";
        int amount = 4;

        Product found = products.getProduct(productName, true);

        PurchaseDTO res = convenienceService.purchase(productName, amount);

        assertEquals(found.getPrice()*2, res.getPromotedPrice());
        assertEquals(amount, res.getAmount());
    }

    @Test
    void 투쁠원_3개구매_테스트() {
        String productName = "탄산수";
        int amount = 3;

        Product found = products.getProduct(productName, true);

        PurchaseDTO res = convenienceService.purchase(productName, amount);

        assertEquals(found.getPrice()*2, res.getPromotedPrice());
        assertEquals(amount, res.getAmount());
    }
}
