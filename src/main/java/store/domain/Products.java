package store.domain;

import store.constant.Constant;
import store.constant.ErrorMessage;
import store.util.Util;
import store.util.Validate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Products {
    private final List<Product> products;

    public Products(Promotions promotions) throws IOException {
        products = new ArrayList<>();
        readFile(promotions);
    }

    private void readFile(Promotions promotions) throws IOException {
        FileReader fr;
        try {
            fr = new FileReader("src/main/resources/products.md");
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_FILE_ROUTE.getMessages());
        }
        parseFile(new BufferedReader(fr), promotions);
    }

    private void parseFile(BufferedReader br, Promotions promotions) throws IOException {
        Util.skipFirstLine(br);
        String line;
        while ( (line = br.readLine()) != null ) {
            products.add(parseProduct(line, promotions));
        }
    }

    private Product parseProduct(String line, Promotions promotions) {
        String[] split = line.split(",");
        Validate.parseProduct(split);

        return new Product(split[Constant.PRODUCT_NAME_INDEX.getValue()],
                        (split[Constant.PRODUCT_PRICE_INDEX.getValue()]),
                        (split[Constant.PRODUCT_QUANTITY_INDEX.getValue()]),
                promotions.getPromotion(split[Constant.PRODUCT_PROMOTION_INDEX.getValue()]));
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
        return products;
    }
}
