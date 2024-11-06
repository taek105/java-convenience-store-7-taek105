package store.view;

import store.domain.Product;
import store.domain.Products;

public class OutputView {
    public void printProducts(Products products) {
        for ( Product product : products.getProductsList() ) {
            StringBuilder sb = new StringBuilder();
            sb.append(product.getName()).append(", ").
                    append(product.getPrice()).append(", ").
                    append(product.getQuantity()).append(", ").
                    append(product.getPromotion().getName()).append(", ");
            System.out.println(sb);
        }
    }
}
