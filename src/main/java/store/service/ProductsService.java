package store.service;

import store.constant.ErrorMessage;
import store.domain.Product;
import store.domain.Products;

public class ProductsService {
    Products products;

    public ProductsService(Products products) {
        this.products = products;
    }

    public int purchase(String productName, int howMuchPurchased) {
        Product promoteProduct = products.getProduct(productName, true);
        Product justProduct = products.getProduct(productName, false);

        if ( howMuchPurchased > promoteProduct.getQuantity() + justProduct.getQuantity() ) {
            throw new IllegalArgumentException(ErrorMessage.EXCEED_QUANTITY.getMessages());
        }

        int amountToPay;
        int howMuchPurchasedNow;
        int[] promoteProductRes = promoteProductPurchase(promoteProduct, howMuchPurchased);

        amountToPay = promoteProductRes[0];
        howMuchPurchasedNow = promoteProductRes[1];

        amountToPay += justProductPurchase(justProduct, howMuchPurchased, howMuchPurchasedNow);

        return amountToPay;
    }

    private static int justProductPurchase(Product justProduct, int howMuchPurchased, int howMuchPurchasedNow) {
        int amountToPay = 0;
        int temp = howMuchPurchasedNow;
        while ( howMuchPurchasedNow < justProduct.getQuantity() &&
                howMuchPurchasedNow < howMuchPurchased) {
            howMuchPurchasedNow ++;
            amountToPay += justProduct.getPrice();
        }
        justProduct.sold((howMuchPurchasedNow-temp));
        return amountToPay;
    }

    private int[] promoteProductPurchase(Product promoteProduct, int howMuchPurchased) {
        int amountToPay = 0;
        int howMuchPurchasedNow = 0;
        while ( howMuchPurchasedNow < promoteProduct.getQuantity() &&
                howMuchPurchasedNow < howMuchPurchased ) {
            howMuchPurchasedNow++;
            amountToPay += promoteProduct.getPrice();
            howMuchPurchasedNow += promotionCheck(howMuchPurchasedNow,
                    promoteProduct.getPromotion().getBuy(),
                    promoteProduct.getQuantity(),
                    promoteProduct.getPromotion().getGet());
        }
        promoteProduct.sold(howMuchPurchasedNow);
        return new int[] {amountToPay, howMuchPurchasedNow};
    }

    private int promotionCheck(int i, int buy, int promoteQuantity, int get) {
        if ( i % buy == 0 ) {
            return promotionGet(i, promoteQuantity, get);
        }
        return 0;
    }

    private int promotionGet(int i, int promoteQuantity, int get) {
        if ( promoteQuantity < get) {
            return promoteQuantity - i;
        }
        return get;
    }

}
