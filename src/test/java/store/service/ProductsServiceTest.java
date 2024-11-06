package store.service;

import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotions;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductsServiceTest {
    Products products = new Products(new Promotions());
    ProductsService productsService = new ProductsService(products);

    public ProductsServiceTest() throws IOException {}

    @Test
    void 투쁠투_3개구매_테스트() {
        String productName = "효택도시락";
        int howMuch = 3;

        Product found = products.getProduct(productName, true);
        int q = found.getQuantity();

        assertEquals(productsService.purchase(productName, howMuch), found.getPrice()*2);
        assertEquals(q-4, found.getQuantity());
    }

    @Test
    void 투쁠투_5개구매_테스트() {
        String productName = "효택도시락";
        int howMuch = 5;

        Product found = products.getProduct(productName, true);
        int q = found.getQuantity();

        int realBoughtQuantity = 0;
        int i = 0;
        while (i < howMuch) {
            i ++;
            realBoughtQuantity ++;
            if ( i %  found.getPromotion().getBuy() == 0) {
                i += found.getPromotion().getGet();
            }
        }

        assertEquals(found.getPrice()*realBoughtQuantity, productsService.purchase(productName, howMuch));
        assertEquals(q-howMuch, found.getQuantity());
    }

    @Test
    void 원쁠삼십_23개구매_테스트() {
        String productName = "효택워터";
        int howMuch = 23;

        Product found = products.getProduct(productName, true);
        int q = found.getQuantity();

        assertEquals(found.getPrice(), productsService.purchase(productName, howMuch));
        assertEquals(q-(found.getPromotion().getGet()+1), found.getQuantity());
    }

    @Test
    void 원쁠삼십_프로모션_재고_초과_테스트() {
        String productName = "효택소세지";
        int howMuch = 33;


        Product found = products.getProduct(productName, true);
        int q = found.getQuantity();
        Product found2 = products.getProduct(productName, false);
        int q2 = found2.getQuantity();


        assertEquals(found.getPrice()*(howMuch-q+1), productsService.purchase(productName, howMuch));
        assertEquals(0, found.getQuantity());
        assertEquals(q2-(howMuch-q), found2.getQuantity());
    }
}
