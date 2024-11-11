package store.domain;

import store.constant.ErrorMessage;
import store.constant.ProductConstant;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    public Product(String[] split, Promotion promotion) {
        this.name = split[ProductConstant.PRODUCT_NAME_INDEX.getValue()];
        this.price = Integer.parseInt(split[ProductConstant.PRODUCT_PRICE_INDEX.getValue()]);
        this.quantity = Integer.parseInt(split[ProductConstant.PRODUCT_QUANTITY_INDEX.getValue()]);
        this.promotion = promotion;
    }

    public Product(String name, int price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return this.promotion;
    }

    public boolean isEmpty() {
        return this.price == ProductConstant.EMPTY_PRODUCT_PRICE.getValue();
    }

    public boolean isPromotion() {
        return promotion.isEmpty();
    }

    public boolean isPromotionNow() {
        return this.promotion.isPromotionNow();
    }

    public boolean isSellable(int quantity) {
        return this.quantity >= quantity;
    }

    public void sold(int quantity) {
        if ( !isSellable(quantity) ) {
            throw new IllegalArgumentException(ErrorMessage.EXCEED_QUANTITY.getMessages());
        }
        this.quantity -= quantity;
    }

    public static Product nullProduct() {
        return new Product(ProductConstant.NULL_PRODUCT_NAME.getStringValue(),
                ProductConstant.EMPTY_PRODUCT_PRICE.getValue(),
                ProductConstant.EMPTY_PRODUCT_QUANTITY.getValue(),
                Promotion.nullPromotion());
    }
}
