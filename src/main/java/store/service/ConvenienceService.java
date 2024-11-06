package store.service;

import store.domain.Promotions;
import store.domain.Products;

import java.io.IOException;

public class ConvenienceService {
    Promotions promotions;
    Products products;

    public ConvenienceService() throws IOException {
        this.promotions = new Promotions();
        this.products = new Products(promotions);
    }


}
