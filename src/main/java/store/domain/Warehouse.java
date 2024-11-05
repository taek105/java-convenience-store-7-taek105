package store.domain;

import store.constant.Constants;
import store.constant.ErrorMessage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private final List<Product> products;

    public Warehouse() {
        products = new ArrayList<>();
        init();
    }

    private void init(){
        try {
            FileReader fr = new FileReader("src/main/resources/products.md");
            readProduct(new BufferedReader(fr));
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_FILEROUTE.getMessages());
        }
    }

    private void readProduct(BufferedReader br) throws IOException {
        String line = br.readLine(); // 입력 양식 빼기

        while ( (line = br.readLine()) != null ) {
            String[] split = line.split(",");
            Product product =
                    new Product(split[Constants.PRODUCT_NAME_INDEX.getValue()],
                            (split[Constants.PRODUCT_PRICE_INDEX.getValue()]),
                            (split[Constants.PRODUCT_QUANTITY_INDEX.getValue()]),
                            (split[Constants.PRODUCT_PROMOTION_INDEX.getValue()]));
            products.add(product);
        }
    }

    private Product getProduct(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        throw new IllegalArgumentException(ErrorMessage.NOT_EXISTENCE_PRODUCT.getMessages());
    }

    public int getProductStock(String name) {
        return getProduct(name).getQuantity();
    }

    public boolean isSellable(String name, int howMuchBought) {
        Product found = getProduct(name);
        return found.isSellable(howMuchBought);
    }

    public void selled(String name, int howMuchBought) {
        getProduct(name).selled(howMuchBought);
    }
}
