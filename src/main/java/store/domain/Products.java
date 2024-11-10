package store.domain;

import store.constant.Constant;
import store.constant.ErrorMessage;
import store.constant.FilePath;
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
        // 프로모션 상품이 있는데 프로모션이 없는 상품이 파일에 없을 때 더미 상품을 추가합니다
        addNoQuantityProduct();
    }

    public final Product getProduct(String name, boolean isPromotion) {
        for (Product product : products) {
            if (product.getName().equals(name) && product.isPromotion() == isPromotion) {
                return product;
            }
        }
        return Product.nullProduct();
    }

    public final List<Product> getProductsList() {
        return Collections.unmodifiableList(products);
    }

    private void readFile(Promotions promotions) throws IOException {
        FileReader fr;
        try {
            fr = new FileReader(FilePath.PRODUCT_MD.getValue());
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
        parseProductValidate(split);

        return new Product(split,
                promotions.getPromotion(split[Constant.PRODUCT_PROMOTION_INDEX.getValue()]));
    }

    private static void parseProductValidate(String[] split) {
        if ( split.length-1 != Constant.PRODUCT_PROMOTION_INDEX.getValue() ) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_FILE_FORMAT.getMessages());
        }
    }

    private void addNoQuantityProduct() {
        List<Product> foundProducts = new ArrayList<>();
        for ( Product product : products ) {
            initFoundProducts(product, foundProducts);
        }

        products.addAll(foundProducts);
    }

    private void initFoundProducts(Product product, List<Product> foundProducts) {
        if ( product.isPromotion() ) {
            if ( getProduct(product.getName(), false).isEmpty() ) {
                foundProducts.add(new Product(product.getName(),
                        product.getPrice(),
                        0,
                        Promotion.nullPromotion()));
            }
        }
    }
}
