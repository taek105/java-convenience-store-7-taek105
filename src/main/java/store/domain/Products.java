package store.domain;

import store.constant.Constant;
import store.constant.ErrorMessage;
import store.util.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
        if ( split.length != Constant.PRODUCT_PROMOTION_INDEX.getValue()+1 ) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_FILE_FORMAT.getMessages());
        }

        return new Product(split[Constant.PRODUCT_NAME_INDEX.getValue()],
                        (split[Constant.PRODUCT_PRICE_INDEX.getValue()]),
                        (split[Constant.PRODUCT_QUANTITY_INDEX.getValue()]),
                promotions.getPromotion(split[Constant.PRODUCT_PROMOTION_INDEX.getValue()]));
    }

    public final Product getProduct(String name) {
        if ( getProduct(name, true).isEmpty() ) {
            return getProduct(name, true);
        }

        return getProduct(name, false);
    }

    public final Product getProduct(String name, boolean isPromotion) {
        for (Product product : products) {
            if (product.getName().equals(name) && product.isPromotion() == isPromotion) {
                return product;
            }
        }
        return Product.emptyProduct();
    }

    public List<Product> getProductsList() {
        return Collections.unmodifiableList(products);
    }
}
