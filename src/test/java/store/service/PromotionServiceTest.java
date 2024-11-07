package store.service;

import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotions;
import store.model.PurchaseResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PromotionServiceTest {
    Products products = new Products(new Promotions());
    PromotionService promotionService = new PromotionService(products);

    public PromotionServiceTest() throws IOException {}

    @Test
    void 투쁠투_프로모션_재고_초과_정가구매_Y_테스트() {
        System.setIn(new ByteArrayInputStream("Y".getBytes()));

        String productName = "효택도시락2";
        int amount = 8;

        PurchaseResult purchaseResult = new PurchaseResult(amount);

        Product found = products.getProduct(productName, true);

        promotionService.promotionQuantityCheck(found, purchaseResult);

        assertEquals(amount, purchaseResult.getAmount());
    }

    @Test
    void 원쁠삼십_프로모션_재고_모자랄때_공짜물품_제공_동의_테스트() {
        System.setIn(new ByteArrayInputStream("Y".getBytes()));

        String productName = "효택소세지";
        int amount = 10;

        Product found = products.getProduct(productName, true);
        PurchaseResult purchaseResult = new PurchaseResult(amount);

        promotionService.askServeExtraProduct(purchaseResult, found);

        assertEquals(found.getQuantity(), purchaseResult.getAmount());
    }

    @Test
    void 투쁠원_공짜물품_제공_동의_테스트() {
        System.setIn(new ByteArrayInputStream("Y".getBytes()));

        String productName = "사이다";
        int amount = 5;

        Product found = products.getProduct(productName, true);
        PurchaseResult purchaseResult = new PurchaseResult(amount);

        promotionService.askServeExtraProduct(purchaseResult, found);
        assertEquals(6, purchaseResult.getAmount());
    }

    @Test
    void 투쁠원_유효하지_않은_공짜물품_제공_동의_테스트() {
        String productName = "사이다";
        int amount = 4;

        Product found = products.getProduct(productName, true);
        PurchaseResult purchaseResult = new PurchaseResult(amount);

        promotionService.askServeExtraProduct(purchaseResult, found);

        assertEquals(amount, purchaseResult.getAmount());
    }
}
