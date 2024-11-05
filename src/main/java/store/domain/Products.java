package store.domain;

import store.constant.Constants;
import store.constant.ErrorMessage;
import store.util.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Products {
    private final List<Product> products;

    public Products(Promotions promotions) {
        products = new ArrayList<>();
        init(promotions);
    }

    private void init(Promotions promotions) {
        try {
            FileReader fr = new FileReader("src/main/resources/products.md");
            readFile(new BufferedReader(fr), promotions);
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_FILEROUTE.getMessages());
        }
    }

    private void readFile(BufferedReader br, Promotions promotions) throws IOException {
        Utils.skipFirstLine(br);

        String line;
        while ( (line = br.readLine()) != null ) {
            products.add(parseProduct(line, promotions));
        }
    }

    private Product parseProduct(String line, Promotions promotions) {
        String[] split = line.split(",");
        return new Product(split[Constants.PRODUCT_NAME_INDEX.getValue()],
                        (split[Constants.PRODUCT_PRICE_INDEX.getValue()]),
                        (split[Constants.PRODUCT_QUANTITY_INDEX.getValue()]),
                promotions.getPromotion(split[Constants.PRODUCT_PROMOTION_INDEX.getValue()]));
    }

    public Product getProduct(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        throw new IllegalArgumentException(ErrorMessage.NOT_EXISTENCE_PRODUCT.getMessages());
    }

    public List<Product> getProducts() {
        return this.products;
    }
}
