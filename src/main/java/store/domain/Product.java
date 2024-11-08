package store.domain;

import store.constant.Constant;
import store.constant.ErrorMessage;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    public Product(String name, String price, String quantity, Promotion promotion) {
        this.name = name;
        this.price = Integer.parseInt(price);
        this.quantity = Integer.parseInt(quantity);
        this.promotion = validPromotion(promotion);
    }

    public Product(String name, int price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = validPromotion(promotion);
    }

    private Promotion validPromotion(Promotion promotion) {
        if ( promotion.isPromotionNow() )
            return promotion;
        return Promotion.nullPromotion();
    }

    public boolean isEmpty() {
        return this.price == Constant.EMPTY_PRODUCT_PRICE.getValue();
    }

    public boolean isPromotion() {
        return promotion.isPromotion();
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

    public static Product emptyProduct() {
        return new Product("",
                Integer.toString(Constant.EMPTY_PRODUCT_PRICE.getValue()),
                "0",
                Promotion.nullPromotion());
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
}
