package store.service;

import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotions;
import store.model.PurchaseDTO;

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

        String name = "효택도시락2";
        int amount = 8;

        PurchaseDTO purchaseDTO = new PurchaseDTO(
                products.getProduct(name, true),
                products.getProduct(name, false),
                amount);

        promotionService.promotionQuantityCheck(purchaseDTO);

        assertEquals(amount, purchaseDTO.getAmount());
    }

    @Test
    void 원쁠삼십_프로모션_재고_모자랄때_공짜물품_제공_동의_테스트() {
        System.setIn(new ByteArrayInputStream("Y".getBytes()));

        String name = "효택소세지";
        int amount = 10;

        PurchaseDTO purchaseDTO = new PurchaseDTO(
                products.getProduct(name, true),
                products.getProduct(name, false),
                amount);
        promotionService.askServeExtraProduct(purchaseDTO);

        Product found = products.getProduct(name, true);

        assertEquals(found.getQuantity(), purchaseDTO.getAmount());
    }

    @Test
    void 투쁠원_공짜물품_제공_동의_테스트() {
        System.setIn(new ByteArrayInputStream("Y".getBytes()));

        String name = "사이다";
        int amount = 5;

        PurchaseDTO purchaseDTO = new PurchaseDTO(
                products.getProduct(name, true),
                products.getProduct(name, false),
                amount);

        promotionService.askServeExtraProduct(purchaseDTO);
        assertEquals(6, purchaseDTO.getAmount());
    }

    @Test
    void 투쁠원_유효하지_않은_공짜물품_제공_동의_테스트() {
        String name = "사이다";
        int amount = 4;

        PurchaseDTO purchaseDTO = new PurchaseDTO(
                products.getProduct(name, true),
                products.getProduct(name, false),
                amount);

        promotionService.askServeExtraProduct(purchaseDTO);

        assertEquals(amount, purchaseDTO.getAmount());
    }
}
