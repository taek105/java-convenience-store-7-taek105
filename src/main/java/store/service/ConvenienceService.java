package store.service;

import store.domain.Promotions;
import store.domain.Products;

public class ConvenienceService {
    Promotions promotions;
    Products products;

    public ConvenienceService() {
        this.promotions = new Promotions();
        this.products = new Products(promotions);
    }


}
