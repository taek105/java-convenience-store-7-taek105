package store.domain;

import store.constant.Constant;
import store.constant.ErrorMessage;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    public Product(String[] split, Promotion promotion) {
        this.name = split[Constant.PRODUCT_NAME_INDEX.getValue()];
        this.price = Integer.parseInt(split[Constant.PRODUCT_PRICE_INDEX.getValue()]);
        this.quantity = Integer.parseInt(split[Constant.PRODUCT_QUANTITY_INDEX.getValue()]);
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
        return this.price == Constant.EMPTY_PRODUCT_PRICE.getValue();
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
        return new Product("null",
                Constant.EMPTY_PRODUCT_PRICE.getValue(),
                Constant.EMPTY_PRODUCT_QUANTITY.getValue(),
                Promotion.nullPromotion());
    }
}
