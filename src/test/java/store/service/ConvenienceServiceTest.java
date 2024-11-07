package store.service;

import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotions;

import java.io.IOException;
import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConvenienceServiceTest {
    Products products = new Products(new Promotions());
    ConvenienceService convenienceService = new ConvenienceService(products);

    public ConvenienceServiceTest() throws IOException {}

    @Test
    void 투쁠투_4개구매_테스트() {
        String productName = "효택도시락";
        int amount = 4;

        Product found = products.getProduct(productName, true);
        int q = found.getQuantity();

        assertEquals(convenienceService.purchase(productName, amount), found.getPrice()*2);
        assertEquals(q- amount, found.getQuantity());
    }

    @Test
    void 투쁠투_3개구매_Y_PROMOTE_테스트() {
        String productName = "효택도시락";
        int amount = 3;

        Product found = products.getProduct(productName, true);
        int q = found.getQuantity();

        assertEquals(convenienceService.purchase(productName, amount), found.getPrice()*2);
        assertEquals(q-4, found.getQuantity());
    }

    @Test
    void 투쁠투_3개구매_N_PROMOTE_테스트() {
        String productName = "효택도시락";
        int amount = 3;

        Product found = products.getProduct(productName, true);
        int q = found.getQuantity();

        assertEquals(convenienceService.purchase(productName, amount), found.getPrice()*2);
        assertEquals(q- amount, found.getQuantity());
    }

    @Test
    void 투쁠투_5개구매_테스트() {
        String productName = "효택도시락";
        int amount = 5;

        Product found = products.getProduct(productName, true);
        int q = found.getQuantity();

        int realBoughtQuantity = 0;
        int i = 0;
        while (i < amount) {
            i ++;
            realBoughtQuantity ++;
            if ( i %  found.getPromotion().getBuy() == 0) {
                i += found.getPromotion().getGet();
            }
        }

        assertEquals(found.getPrice()*realBoughtQuantity, convenienceService.purchase(productName, amount));
        assertEquals(q- amount, found.getQuantity());
    }

    @Test
    void 투쁠투_프로모션_재고_초과_정가구매_Y_테스트() {
        System.setIn(new ByteArrayInputStream("Y".getBytes()));

        String productName = "효택도시락2";
        int amount = 8;

        Product found = products.getProduct(productName, true);
        Product found2 = products.getProduct(productName, false);

        assertEquals(found.getPrice()*6, convenienceService.purchase(productName, amount));
        assertEquals(0, found.getQuantity());
        assertEquals(1, found2.getQuantity());
    }

    @Test
    void 원쁠삼십_23개구매_테스트() {
        String productName = "효택워터";
        int amount = 23;

        Product found = products.getProduct(productName, true);
        int q = found.getQuantity();

        assertEquals(found.getPrice(), convenienceService.purchase(productName, amount));
        assertEquals(q-(found.getPromotion().getGet()+1), found.getQuantity());
    }

    @Test
    void 원쁠삼십_프로모션_재고_초과_정가구매_Y_테스트() {
        System.setIn(new ByteArrayInputStream("Y".getBytes()));

        String productName = "효택소세지";
        int amount = 33;

        Product found = products.getProduct(productName, true);
        int q = found.getQuantity();
        Product found2 = products.getProduct(productName, false);
        int q2 = found2.getQuantity();

        assertEquals(found.getPrice()*(amount-q+1), convenienceService.purchase(productName, amount));
        assertEquals(0, found.getQuantity());
        assertEquals(q2-(amount-q), found2.getQuantity());
    }

    @Test
    void 원쁠삼십_프로모션_재고_초과_정가구매_N_테스트() {
        System.setIn(new ByteArrayInputStream("N".getBytes()));

        String productName = "효택소세지";
        int amount = 33;

        Product found = products.getProduct(productName, true);

        assertEquals(found.getPrice(), convenienceService.purchase(productName, amount));
        assertEquals(0, found.getQuantity());
    }
}
