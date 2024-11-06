package store.service;

import store.domain.Promotions;
import store.domain.Products;

import java.io.IOException;

public class ConvenienceService {
    Products products;

    public ConvenienceService() throws IOException {
        this.products = new Products(new Promotions());
    }


}
